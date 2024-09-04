package formatter.formatApplicator

import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class AssignationEqualWrapWhistepaces : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST, config: FormatterConfig): List<Token> {
        if (ast !is VariableAssignation || !config.assignation_equal_wrap_whitespaces) return tokens

        val resultTokens = tokens.toMutableList()
        val equalIndex = tokens.indexOfFirst { it.type == TokenType.ASSIGN }
        // TODO: correr los position de todos los tokens despues del whitespace agregado
        if (equalIndex != -1) {
            if (equalIndex > 0 && tokens[equalIndex - 1].type != TokenType.WHITESPACE) {
                resultTokens.add(equalIndex, Token(TokenType.WHITESPACE, " ", tokens[equalIndex].position))
            }

            if (equalIndex < tokens.size - 1 && tokens[equalIndex + 1].type != TokenType.WHITESPACE) {
                resultTokens.add(equalIndex + 2, Token(TokenType.WHITESPACE, " ", tokens[equalIndex].position))
            }
        }
        return resultTokens
    }
}
