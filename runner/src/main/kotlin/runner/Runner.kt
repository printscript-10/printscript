package runner

import formatter.FormatSuccess
import formatter.Formatter
import formatter.FormatterConfig
import interpreter.InterpretSuccess
import interpreter.Interpreter
import interpreter.Variable
import lexer.Lexer
import lexer.LexingSuccess
import linter.Linter
import linter.LinterConfig
import parser.ParseSuccess
import parser.Parser
import utils.AST
import utils.EnvProvider
import utils.ErrorHandler
import utils.Failure
import utils.InputProvider
import utils.OutputProvider
import utils.Token
import utils.TokenType
import utils.VariableType
import java.io.InputStream

class Runner(private val version: String) {
    private val lexer = Lexer(version)

    fun validate(input: InputStream, handler: ErrorHandler) {
        processInput(input, handler) { _, _ -> return@processInput }
    }

    fun execute(
        input: InputStream,
        outputProvider: OutputProvider,
        handler: ErrorHandler,
        inputProvider: InputProvider,
        envProvider: EnvProvider,
    ) {
        var variableMap: Map<String, Variable> = mapOf()

        processInput(input, handler) { _, ast ->
            val interpreter = Interpreter(version, variableMap, outputProvider, inputProvider, envProvider)
            val interpretResult = interpreter.interpret(ast)
            if (interpretResult is Failure) return@processInput handler.reportError(interpretResult.error)
            variableMap = (interpretResult as InterpretSuccess).result
        }
    }

    fun format(input: InputStream, handler: ErrorHandler, config: FormatterConfig): String? {
        val formatter = Formatter(config)
        val formattingErrors = mutableListOf<String>()
        var formattedSnippet = ""

        processInput(input, handler) { tokens, ast ->
            val formatResult = formatter.format(tokens, ast)
            if (formatResult is Failure) {
                formattingErrors.add(formatResult.error)
            } else formattedSnippet += (formatResult as FormatSuccess).result
        }
        if (formattingErrors.isNotEmpty()) {
            handler.reportError(formattingErrors.joinToString("\n"))
            return null
        }
        return formattedSnippet
    }

    fun analyze(input: InputStream, handler: ErrorHandler, config: LinterConfig) {
        val linter = Linter(config)
        val lintingErrors = mutableListOf<String>()

        processInput(input, handler) { _, ast ->
            val lintResult = linter.execute(ast)
            if (lintResult is Failure) lintingErrors.add(lintResult.error)
        }
        if (lintingErrors.isNotEmpty()) return handler.reportError(lintingErrors.joinToString("\n"))
    }

    private fun processInput(input: InputStream, handler: ErrorHandler, function: (List<Token>, AST) -> Unit) {
        var variableTypes: Map<String, VariableType> = mapOf()
        var tokenBuffer = mutableListOf<Token>()
        var braceCount = 0
        var actualIndex = 0
        var lineCounter = 0

        input.bufferedReader().use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                if (line.isNotBlank()) {
                    val lexingResult = lexer.tokenize(line, lineCounter)
                    if (lexingResult is Failure) return handler.reportError(lexingResult.error)
                    tokenBuffer.addAll((lexingResult as LexingSuccess).tokens)

                    while (true) {
                        val (statement, updatedBraceCount) = getStatement(tokenBuffer, actualIndex, braceCount)
                        braceCount = updatedBraceCount

                        if (statement == null) {
                            actualIndex = tokenBuffer.size
                            break
                        } else {
                            val tokens = tokenBuffer.subList(0, statement + 1)
                            tokenBuffer = tokenBuffer.subList(statement + 1, tokenBuffer.size)
                            actualIndex = 0

                            val parser = Parser(version, variableTypes)
                            val buildResult = parser.buildAST(tokens)
                            if (buildResult is Failure) return handler.reportError(buildResult.error)
                            variableTypes = (buildResult as ParseSuccess).variables

                            function(tokens, buildResult.result)
                        }
                    }
                }
                line = reader.readLine()
                lineCounter++
            }

            if (braceCount != 0 || tokenBuffer.isNotEmpty()) {
                println("braceCount: " + braceCount)
                println("tokenBuffer: " + tokenBuffer)
                return handler.reportError("Mismatched braces or unclosed statement")
            }
        }
    }

    private fun getStatement(tokens: List<Token>, actualIndex: Int, braceCount: Int): Pair<Int?, Int> {
        var updatedBraceCount = braceCount
        var i = actualIndex

        if (i != 0 && tokens[i - 1].type == TokenType.CLOSE_BRACE && tokens[i].type != TokenType.ELSE) {
            return Pair(i - 1, updatedBraceCount)
        }

        while (i < tokens.size) {
            val token = tokens[i]
            when (token.type) {
                TokenType.SEMICOLON -> if (updatedBraceCount == 0) return Pair(i, updatedBraceCount)
                TokenType.OPEN_BRACE -> updatedBraceCount++
                TokenType.CLOSE_BRACE -> {
                    updatedBraceCount--
                    if (updatedBraceCount == 0 && i + 1 < tokens.size && tokens[i + 1].type != TokenType.ELSE) {
                        return Pair(i, updatedBraceCount)
                    }
                }
                else -> {}
            }
            i++
        }
        return Pair(null, updatedBraceCount)
    }
}
