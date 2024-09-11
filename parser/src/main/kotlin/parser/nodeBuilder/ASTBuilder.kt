package parser.nodeBuilder

import utils.Result
import utils.Token
import utils.TokenType

class ASTBuilder(version: String) {

    private val builders: Map<TokenType, ASTNodeBuilder> = mapOf(
        TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(version),
        TokenType.IDENTIFIER to VariableAssignationBuilder(version),
        TokenType.PRINT to PrintBuilder(version),
    )

    fun build(tokens: List<Token>): Result {
        if (tokens[tokens.size - 1].type != TokenType.SEMICOLON) {
            return BuildFailure("Line must finish with ;")
        }

        val builder = builders[tokens[0].type] ?: return BuildFailure("Invalid line")

        return (builder.build(tokens, 0))
    }
}
