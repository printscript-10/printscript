package formatter.formatApplicator

import formatter.FormatterConfig
import utils.AST
import utils.Position
import utils.PrintFunction
import utils.Token
import utils.TokenType

class PrintTrailingLinejumpApplicator : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST, config: FormatterConfig): List<Token> {
        if (ast !is PrintFunction) return tokens
        val linejumpAmount = config.println_trailing_linejump
        if (linejumpAmount < 0 || linejumpAmount > 2) throw IllegalArgumentException("No pinta throwear esto aca")
        val resultTokens = tokens.toMutableList()
        repeat(linejumpAmount) {
            resultTokens.add(0, Token(TokenType.WHITESPACE, "\n", Position(tokens.first().position.line, 0, 0)))
        }
        return resultTokens
    }
}
