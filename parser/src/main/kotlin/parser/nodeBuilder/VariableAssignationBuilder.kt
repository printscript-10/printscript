package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class VariableAssignationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): BuildResult {
        if (
            (tokens[position].type != TokenType.IDENTIFIER) ||
            (tokens[position + 1].type != TokenType.ASSIGN)
        ) {
            return BuildFailure(
                error = "Invalid variable assignation format",
                position = position,
            )
        }

        val identifier = IdentifierBuilder().build(tokens, position).result as Identifier
        val expressionTokens = getExpression(tokens)
        if(expressionTokens == null){
            return BuildFailure(error = "Assignation cannot be empty", position = position)
        }
        val expressionResult = ExpressionBuilder().build(expressionTokens, position)
        if (expressionResult is BuildFailure) {
            return expressionResult
        }
        val value = (expressionResult as BuildSuccess).result as Expression
        return BuildSuccess(
            result = VariableAssignation(
                id = identifier,
                value = value,
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
