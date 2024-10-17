package parser.nodeBuilder

import result.Result
import token.Token
import token.TokenType

class ASTBuilder(version: String) {

    private val builders: Map<TokenType, ASTNodeBuilder>

    init {
        builders = getBuilders(version)
    }

    fun build(tokens: List<Token>): Result {
        if (
            tokens[tokens.size - 1].type != TokenType.SEMICOLON &&
            tokens[tokens.size - 1].type != TokenType.CLOSE_BRACE
        ) {
            return BuildFailure("Line must finish with ;")
        }

        val builder = builders[tokens[0].type] ?: return BuildFailure("Invalid line")

        return (builder.build(tokens, 0))
    }

    private fun getBuilders(version: String): Map<TokenType, ASTNodeBuilder> {
        val baseMap = mapOf(
            TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(version),
            TokenType.IDENTIFIER to VariableAssignationBuilder(version),
            TokenType.PRINT to PrintBuilder(version),
        )
        return when (version) {
            "1.0" -> baseMap
            "1.1" -> {
                baseMap.toMutableMap().apply {
                    putAll(
                        mapOf(
                            TokenType.IF to IfStatementBuilder(version),
                        ),
                    )
                }.toMap()
            }
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
