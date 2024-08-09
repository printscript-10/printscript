import lexer.Lexer

fun main() {
    val lexer = Lexer()

    val misTokensitos = lexer.tokenize("let a: string = \"hola\";")
    print(misTokensitos)
}