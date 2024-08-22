import lexer.Lexer
import parser.Parser

fun main() {
    val lexer = Lexer()
    val parser = Parser()

    val misTokensitos = lexer.tokenize("let a: string = \"hola\";")
    val misASTsitos = parser.buildAST(misTokensitos)
    print(misASTsitos)
}
