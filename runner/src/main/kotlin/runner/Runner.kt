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
import utils.ErrorHandler
import utils.Failure
import utils.OutputProvider
import utils.Token
import utils.VariableType
import java.io.InputStream

class Runner(private val version: String) {
    private val lexer = Lexer(version)

    fun validate(input: InputStream, handler: ErrorHandler) {
        processInput(input, handler) { _, _ -> return@processInput }
    }

    fun execute(input: InputStream, outputProvider: OutputProvider, handler: ErrorHandler) {
        var variableMap: Map<String, Variable> = mapOf()

        processInput(input, handler) { tokens, ast ->
            val interpreter = Interpreter(variableMap, outputProvider)
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

        processInput(input, handler) { tokens, ast ->
            val lintResult = linter.execute(ast)
            if (lintResult is Failure) lintingErrors.add(lintResult.error)
        }
        if (lintingErrors.isNotEmpty()) return handler.reportError(lintingErrors.joinToString("\n"))
    }

    private fun processInput(input: InputStream, handler: ErrorHandler, function: (List<Token>, AST) -> Unit) {
        var variableTypes: Map<String, VariableType> = mapOf()
        var lineCounter = 0

        input.bufferedReader().use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                if (line.isNotBlank()) {
                    val lexingResult = lexer.tokenize(line, lineCounter)
                    if (lexingResult is Failure) return handler.reportError(lexingResult.error)
                    val tokens = (lexingResult as LexingSuccess).tokens

                    val parser = Parser(variableTypes)
                    val buildResult = parser.buildAST(tokens)
                    if (buildResult is Failure) return handler.reportError(buildResult.error)
                    variableTypes = (buildResult as ParseSuccess).variables

                    function(tokens, buildResult.result)
                }
                line = reader.readLine()
                lineCounter++
            }
        }
    }
}
