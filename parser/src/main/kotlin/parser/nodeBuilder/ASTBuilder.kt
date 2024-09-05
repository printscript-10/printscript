package parser.nodeBuilder

import utils.Result
import utils.Token
import utils.TokenType

class ASTBuilder {

    private val builders: Map<TokenType, ASTNodeBuilder> = mapOf(
        TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(),
        TokenType.IDENTIFIER to VariableAssignationBuilder(),
        TokenType.PRINT to PrintBuilder(),
    )

    fun build(tokens: List<Token>): Result {
        if (tokens[tokens.size - 1].type != TokenType.SEMICOLON) {
            return BuildFailure(
                "Line must finish with ;",
                tokens.size - 1,
            )
        }

        val builder = builders[tokens[0].type] ?: return BuildFailure("Invalid line", tokens.first().position.line)

        return (builder.build(tokens, 0))
    }
}
