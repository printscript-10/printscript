package formatter.formatApplicator

import ast.AST
import ast.VariableAssignation
import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import token.Token
import token.TokenType

class AssignationEqualWrapWhitespacesApplicator(private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        if (ast !is VariableAssignation && config.assignation_equal_wrap_whitespaces != true) {
            return FormatApplicatorSuccess(tokens)
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
