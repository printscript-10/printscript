import interpreter.Interpreter
import lexer.Lexer
import parser.Parser
import parser.semanticAnalizer.ParseFailure
import parser.semanticAnalizer.ParseSuccess
import java.io.File

val filePath = "main/src/main/resources/test.txt"

fun main() {
    val file = File(filePath)
    val parser = Parser()
    val lexer = Lexer()
    val interpreter = Interpreter()
    file.bufferedReader().use { reader ->
        var line: String? = reader.readLine()
        while (line != null) {
            val tokens = lexer.tokenize(line)
            val buildResult = parser.buildAST(tokens)
            if(buildResult is ParseSuccess){
                interpreter.interpret(buildResult.result)
            }
            else{
                println((buildResult as ParseFailure).error)
            }
            line = reader.readLine()
        }
    }
}
