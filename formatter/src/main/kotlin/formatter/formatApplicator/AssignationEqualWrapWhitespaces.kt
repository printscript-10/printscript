package formatter.formatApplicator

import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class AssignationEqualWrapWhitespaces(private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        if (ast !is VariableAssignation || !config.assignation_equal_wrap_whitespaces) {
            return FormatApplicatorSuccess(
                tokens,
            )
        }

        val resultTokens = tokens.toMutableList()
        val equalIndex = tokens.indexOfFirst { it.type == TokenType.ASSIGN }
        if (equalIndex != -1) {
            if (equalIndex > 0 && tokens[equalIndex - 1].type != TokenType.WHITESPACE) {
                resultTokens.add(equalIndex, Token(TokenType.WHITESPACE, " ", tokens[equalIndex].position))
            }

            if (equalIndex < tokens.size - 1 && tokens[equalIndex + 1].type != TokenType.WHITESPACE) {
                resultTokens.add(equalIndex + 2, Token(TokenType.WHITESPACE, " ", tokens[equalIndex].position))
            }
        }
        return FormatApplicatorSuccess(resultTokens)
    }
}
