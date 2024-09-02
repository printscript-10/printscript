package runner

import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.Interpreter
import interpreter.Variable
import lexer.Lexer
import lexer.LexingFailure
import lexer.LexingSuccess
import parser.Parser
import parser.semanticAnalizer.ParseFailure
import parser.semanticAnalizer.ParseSuccess
import utils.ErrorHandler
import utils.VariableType
import java.io.InputStream

class Runner {
    fun run(input: InputStream, handler: ErrorHandler) {
        val lexer = Lexer()
        var variableTypes: Map<String, VariableType> = mapOf()
        var variableMap: Map<String, Variable> = mapOf()
        var lineCounter = 0
        input.bufferedReader().use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                val lexingResult = lexer.tokenize(line, lineCounter)
                if (lexingResult is LexingFailure) return handler.reportError(lexingResult.error)
                val tokens = (lexingResult as LexingSuccess).tokens

                val parser = Parser(variableTypes)
                val buildResult = parser.buildAST(tokens)
                if (buildResult is ParseFailure) return handler.reportError(buildResult.error)
                variableTypes = (buildResult as ParseSuccess).variables

                val interpreter = Interpreter(variableMap)
                val interpretResult = interpreter.interpret(buildResult.result)
                if (interpretResult is InterpretFailure) return handler.reportError(interpretResult.error)
                variableMap = (interpretResult as InterpretSuccess).result

                line = reader.readLine()
                lineCounter++
            }
        }
    }
}
