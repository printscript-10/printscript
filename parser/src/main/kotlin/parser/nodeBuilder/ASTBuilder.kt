package parser.nodeBuilder

import utils.Result
import utils.Token
import utils.TokenType

class ASTBuilder(val version: String) {

    private val builders: Map<String, Map<TokenType, ASTNodeBuilder>> = mapOf(
        "1.0" to mapOf(
            TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(),
            TokenType.IDENTIFIER to VariableAssignationBuilder(),
            TokenType.PRINT to PrintBuilder(),
        ),
        "1.1" to mapOf(
            TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(),
            TokenType.IDENTIFIER to VariableAssignationBuilder(),
            TokenType.PRINT to PrintBuilder(),
            TokenType.IF to IfStatementBuilder(),
        )
    )

    fun build(tokens: List<Token>): Result {
        val astBuilders = builders[version]
        if(astBuilders == null) throw Error("Unsupported version ${version}")
        if (tokens[tokens.size - 1].type != TokenType.SEMICOLON) {
            return BuildFailure(
                "Line must finish with ;",
                tokens.size - 1,
            )
        }

        val builder = astBuilders[tokens[0].type] ?: return BuildFailure("Invalid line", tokens.first().position.line)

        return (builder.build(tokens, 0))
    }
}
