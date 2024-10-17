package runner

import ast.AST
import ast.VariableType
import formatter.FormatApplicatorError
import formatter.FormatApplicatorSuccess
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
import provider.EnvProvider
import provider.ErrorHandler
import provider.InputProvider
import provider.OutputProvider
import result.Failure
import token.Token
import token.TokenType
import java.io.InputStream
import kotlin.math.roundToInt

class Runner(private val version: String) {
    private val lexer = Lexer(version)

    fun validate(input: InputStream, handler: ErrorHandler) {
        processInput(input, handler) { _, _, _ -> return@processInput }
    }

    fun execute(
        input: InputStream,
        outputProvider: OutputProvider,
        handler: ErrorHandler,
        inputProvider: InputProvider,
        envProvider: EnvProvider,
    ) {
        var variableMap: Map<String, Variable> = mapOf()

        processInput(input, handler) { _, ast, _ ->
            val interpreter = Interpreter(version, variableMap, outputProvider, inputProvider, envProvider)
            val interpretResult = interpreter.interpret(ast)
            if (interpretResult is Failure) return@processInput handler.reportError(interpretResult.error)
            variableMap = (interpretResult as InterpretSuccess).result
        }
    }

    fun format(
        input: InputStream,
        handler: ErrorHandler,
        config: FormatterConfig,
        outputProvider: OutputProvider,
    ): String? {
        val formatter = Formatter(config, version)
        val formattingErrors = mutableListOf<String>()
        var formattedSnippet: String? = ""

        val hasError = MutableBoolean()
        val inputString = input.bufferedReader().use { it.readText() }
        val totalLines = inputString.lines().size

        processInput(inputString.byteInputStream(), handler, hasError) { tokens, ast, line ->

            val tokensResult = formatter.format(tokens, ast)
            if (tokensResult is FormatApplicatorError) {
                formattingErrors.add(tokensResult.message)
            } else {
                formattedSnippet += formatter.concatenateTokenValues((tokensResult as FormatApplicatorSuccess).tokens)
            }

            val percentage = ((line.toDouble() / totalLines) * 100).roundToInt()
            outputProvider.print("\rProgress: |${progressBar(percentage)}| $percentage% formatted")
        }
        if (hasError.value) return null

        if (formattingErrors.isNotEmpty()) {
            handler.reportError(formattingErrors.joinToString("\n"))
            return null
        }

        outputProvider.print("\rProgress: |${progressBar(100)}| 100% linted")
        return formattedSnippet
    }

    fun analyze(input: InputStream, handler: ErrorHandler, config: LinterConfig, outputProvider: OutputProvider) {
        val linter = Linter(config, version)
        val lintingErrors = mutableListOf<String>()

        val hasError = MutableBoolean()
        val inputString = input.bufferedReader().use { it.readText() }
        val totalLines = inputString.lines().size

        processInput(inputString.byteInputStream(), handler, hasError) { _, ast, line ->
            val lintResult = linter.execute(ast)
            if (lintResult is Failure) lintingErrors.add(lintResult.error)

            val percentage = ((line.toDouble() / totalLines) * 100).roundToInt()
            outputProvider.print("\rProgress: |${progressBar(percentage)}| $percentage% linted")
        }
        if (hasError.value) return

        if (lintingErrors.isNotEmpty()) return handler.reportError(lintingErrors.joinToString("\n"))

        outputProvider.print("\rProgress: |${progressBar(100)}| 100% linted")
    }

    private fun processInput(
        input: InputStream,
        handler: ErrorHandler,
        hasError: MutableBoolean? = null,
        function: (List<Token>, AST, Int) -> Unit,
    ) {
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
                    if (lexingResult is Failure) {
                        if (hasError != null) hasError.value = true
                        return handler.reportError(lexingResult.error)
                    }
                    tokenBuffer.addAll((lexingResult as LexingSuccess).tokens)

                    while (true) {
                        val (statement, updatedBraceCount) = getStatement(tokenBuffer, actualIndex, braceCount)
                        braceCount = updatedBraceCount

                        if (statement == null) {
                            actualIndex = tokenBuffer.size
                            break
                        } else {
                            val tokens = tokenBuffer.subList(0, statement + 1).toList()
                            tokenBuffer = tokenBuffer.subList(statement + 1, tokenBuffer.size).toMutableList()
                            actualIndex = 0

                            val parser = Parser(version, variableTypes)
                            val buildResult = parser.buildAST(tokens)
                            if (buildResult is Failure) {
                                if (hasError != null) hasError.value = true
                                return handler.reportError(buildResult.error)
                            }
                            variableTypes = (buildResult as ParseSuccess).variables

                            function(tokens, buildResult.result, lineCounter + 1)
                        }
                    }
                }
                line = reader.readLine()
                lineCounter++
            }

            if (braceCount != 0 || tokenBuffer.isNotEmpty()) {
                if (hasError != null) hasError.value = true
                return handler.reportError("Mismatched braces or unclosed statement")
            }
        }
    }

    private fun getStatement(tokens: List<Token>, actualIndex: Int, braceCount: Int): Pair<Int?, Int> {
        var updatedBraceCount = braceCount
        var i = actualIndex

        if (
            i != 0 &&
            braceCount == 0 &&
            tokens[i - 1].type == TokenType.CLOSE_BRACE &&
            tokens[i].type != TokenType.ELSE
        ) {
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

    private fun progressBar(percentage: Int): String {
        val barLength = 50 // Total length of the progress bar
        val filledLength = (barLength * percentage) / 100
        return "█".repeat(filledLength) + "-".repeat(barLength - filledLength)
    }
}
