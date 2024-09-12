package formatter.formatApplicator

import formatter.FormatApplicatorSuccess
import utils.AST
import utils.Token
import utils.TokenType

class MandatoryWhitespaceApplicator(version: String) : FormatApplicator {

    private val mandatoryWhitespacePairs: List<Pair<TokenType, TokenType>>

    init {
        mandatoryWhitespacePairs = getMandatoryPairs(version)
    }
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        val resultTokens = tokens.toMutableList()

        for (i in 0 until resultTokens.size - 1) {
            val currentToken = resultTokens[i]
            val nextToken = resultTokens[i + 1]

            if (mandatoryWhitespacePairs.any { it.first == currentToken.type && it.second == nextToken.type }) {
                resultTokens.add(i + 1, Token(TokenType.WHITESPACE, " ", nextToken.position))
            }
        }

        return FormatApplicatorSuccess(resultTokens)
    }

    private fun getMandatoryPairs(version: String): List<Pair<TokenType, TokenType>> {
        val basePairs = listOf(
            Pair(TokenType.VARIABLE_DECLARATOR, TokenType.IDENTIFIER),
        )

        return when (version) {
            "1.0" -> basePairs
            "1.1" -> basePairs.toMutableList().apply {
                addAll(
                    listOf(
                        Pair(TokenType.IF, TokenType.OPEN_BRACKET),
                        Pair(TokenType.CLOSE_BRACKET, TokenType.OPEN_BRACE),
                        Pair(TokenType.CLOSE_BRACE, TokenType.ELSE),
                        Pair(TokenType.ELSE, TokenType.OPEN_BRACE),
                    ),
                )
            }
            else -> throw IllegalArgumentException("Unsupported version $version")
        }
    }
}
