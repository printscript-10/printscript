package formatter.formatApplicator

import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType

class MandatoryWhitespaceApplicator(private val config: FormatterConfig) : FormatApplicator {

    private val mandatoryWhitespacePairs: List<Pair<TokenType, TokenType>> = listOf(
        Pair(TokenType.VARIABLE_DECLARATOR, TokenType.IDENTIFIER),
    )
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        val resultTokens = tokens.toMutableList()

        for (i in 0 until tokens.size - 1) {
            val currentToken = tokens[i]
            val nextToken = tokens[i + 1]

            if (mandatoryWhitespacePairs.any { it.first == currentToken.type && it.second == nextToken.type }) {
                if (nextToken.type != TokenType.WHITESPACE) {
                    resultTokens.add(i + 1, Token(TokenType.WHITESPACE, " ", nextToken.position))
                }
            }
        }

        return FormatApplicatorSuccess(resultTokens)
    }
}
