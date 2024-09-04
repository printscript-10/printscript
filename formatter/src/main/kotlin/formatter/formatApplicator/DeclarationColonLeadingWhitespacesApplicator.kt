package formatter.formatApplicator

import formatter.FormatterConfig
import utils.AST
import utils.Token
import utils.TokenType
import utils.VariableDeclaration

class DeclarationColonLeadingWhitespacesApplicator : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST, config: FormatterConfig): List<Token> {
        if (ast !is VariableDeclaration || !config.declaration_colon_leading_whitespaces) return tokens

        val resultTokens = tokens.toMutableList()
        val colonIndex = tokens.indexOfFirst { it.type == TokenType.COLON }
        // TODO: correr los position de todos los tokens despues del whitespace agregado
        if (colonIndex != -1) {
            resultTokens.add(colonIndex + 1, Token(TokenType.WHITESPACE, " ", tokens[colonIndex].position))
        }
        return resultTokens
    }
}
