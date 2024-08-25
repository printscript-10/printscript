package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Literal
import utils.PrintFunction
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration

class PrintBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val openBraceIndex = position + 1
        if (tokens[openBraceIndex].type != TokenType.OPEN_BRACE) {
            return BuildFailure(
                error = "Invalid print function format",
                position = position,
            )
        }

        val expressionTokens = getExpression(tokens)
        if(expressionTokens == null){
            return BuildFailure("Empty expression at ${position}", position)
        }
        val expressionResult = ExpressionBuilder().build(expressionTokens, position)
        if (expressionResult is BuildFailure) {
            return expressionResult
        }
        val expression = (expressionResult as BuildSuccess).result as Expression

        return BuildSuccess(
            result = PrintFunction(
                value = expression,
                position = tokens[position].position
            ),
            position = position,
        )
    }

    private fun getExpression(tokens: List<Token>): List<Token>?{
        val openBraceIndex = tokens.indexOfFirst { it.type == TokenType.OPEN_BRACE }
        val closingBraceIndex = tokens.indexOfFirst { it.type == TokenType.CLOSE_BRACE }
        if(openBraceIndex == -1 || closingBraceIndex == -1){
            return null
        }
        return tokens.subList(openBraceIndex + 1, closingBraceIndex)
    }
}
