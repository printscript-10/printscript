package formatter.formatApplicator

import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType

class BinaryOperatorWrapWhitespacesApplicator(private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        val resultTokens = tokens.toMutableList()
        var i = 0
        while (i < resultTokens.size) {
            val token = resultTokens[i]

            if (token.type == TokenType.BINARY_OPERATOR) {
                if (i > 0 && resultTokens[i - 1].type != TokenType.WHITESPACE) {
                    resultTokens.add(i, Token(TokenType.WHITESPACE, " ", token.position))
                    i++
                } else if (i > 0 && resultTokens[i - 1].type == TokenType.WHITESPACE) {
                    while (i > 1 && resultTokens[i - 2].type == TokenType.WHITESPACE) {
                        resultTokens.removeAt(i - 2)
                        i--
                    }
                }

                if (i < resultTokens.size - 1 && resultTokens[i + 1].type != TokenType.WHITESPACE) {
                    resultTokens.add(i + 1, Token(TokenType.WHITESPACE, " ", token.position))
                } else if (i < resultTokens.size - 1 && resultTokens[i + 1].type == TokenType.WHITESPACE) {
                    while (i + 2 < resultTokens.size && resultTokens[i + 2].type == TokenType.WHITESPACE) {
                        resultTokens.removeAt(i + 2)
                    }
                }
            }
            i++
        }
        return FormatApplicatorSuccess(resultTokens)
    }
}
