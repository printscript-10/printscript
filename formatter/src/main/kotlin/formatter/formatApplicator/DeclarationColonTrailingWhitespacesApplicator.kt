package formatter.formatApplicator

import ast.AST
import ast.VariableDeclaration
import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import token.Token
import token.TokenType

class DeclarationColonTrailingWhitespacesApplicator(private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorSuccess {
        if (ast !is VariableDeclaration || config.declaration_colon_trailing_whitespaces != true) {
            return FormatApplicatorSuccess(tokens)
        }

        val resultTokens = tokens.toMutableList()
        val colonIndex = tokens.indexOfFirst { it.type == TokenType.COLON }
        if (colonIndex != -1) {
            resultTokens.add(colonIndex, Token(TokenType.WHITESPACE, " ", tokens[colonIndex].position))
        }
        return FormatApplicatorSuccess(resultTokens)
    }
}
