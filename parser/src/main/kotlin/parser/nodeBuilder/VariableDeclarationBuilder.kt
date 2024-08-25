package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableAssignation
import utils.VariableDeclaration

class VariableDeclarationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val idIndex = position + 1
        val typeIndex = position + 3
        if (
            (tokens[position].type != TokenType.VARIABLE_DECLARATOR) ||
            (tokens[idIndex].type != TokenType.IDENTIFIER) ||
            (tokens[position + 2].type != TokenType.COLON) ||
            (tokens[typeIndex].type != TokenType.TYPE)
        ) {
            return BuildFailure(
                error = "Invalid variable declaration format",
                position = position,
            )
        }
        val type = TypeBuilder().build(tokens, typeIndex).result as Type
        val identifier = IdentifierBuilder().build(tokens, idIndex).result as Identifier
        val expressionTokens = getExpression(tokens)
        var value: Expression? = null
        if(expressionTokens != null){
            val expressionResult = ExpressionBuilder().build(expressionTokens, position)
            if (expressionResult is BuildFailure) {
                return expressionResult
            }
            value = (expressionResult as BuildSuccess).result as Expression
        }

        return BuildSuccess(
            result = VariableDeclaration(
                id = identifier,
                init = value,
                type = type,
                position = tokens[position].position,
            ),
            position = position,
        )
    }

    private fun getExpression(tokens: List<Token>): List<Token>?{
        val equalSignIndex = tokens.indexOfFirst { it.type == TokenType.ASSIGN }
        val semicolonIndex = tokens.indexOfFirst { it.type == TokenType.SEMICOLON }
        if(equalSignIndex == -1 || semicolonIndex == -1){
            return null
        }
        return tokens.subList(equalSignIndex + 1, semicolonIndex)
    }
}
