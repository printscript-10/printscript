package formatter.formatApplicator

import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType
import utils.VariableDeclaration

class DeclarationColonTrailingWhitespacesApplicator : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST, config: FormatterConfig): List<Token> {
        if (ast !is VariableDeclaration || !config.declaration_colon_trailing_whitespaces) return tokens

        val resultTokens = tokens.toMutableList()
        val colonIndex = tokens.indexOfFirst { it.type == TokenType.COLON }
        // TODO: correr los position de todos los tokens despues del whitespace agregado
        if (colonIndex != -1) {
            resultTokens.add(colonIndex, Token(TokenType.WHITESPACE, " ", tokens[colonIndex].position))
        }
        return resultTokens
    }
}
