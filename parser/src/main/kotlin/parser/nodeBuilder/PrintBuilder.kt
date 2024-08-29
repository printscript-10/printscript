package parser.nodeBuilder

import utils.Expression
import utils.PrintFunction
import utils.Token
import utils.TokenType

class PrintBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val openBraceIndex = position + 1
        val closingBraceIndex = tokens.size - 2
        if (
            (tokens[position].type != TokenType.PRINT) ||
            (tokens[openBraceIndex].type != TokenType.OPEN_BRACE) ||
            (tokens[closingBraceIndex].type != TokenType.CLOSE_BRACE)
        ) {
            return BuildFailure(
                error = "Invalid print function format",
                position = position,
            )
        }

        val expressionTokens = tokens.subList(openBraceIndex + 1, closingBraceIndex)
        if (expressionTokens.isEmpty()) {
            return BuildFailure(
                error = "Print function cannot be empty",
                position = position,
            )
        }

        val expressionResult = ExpressionBuilder().build(expressionTokens, position)
        if (expressionResult is BuildFailure) return expressionResult

        return BuildSuccess(
            result = PrintFunction(
                value = (expressionResult as BuildSuccess).result as Expression,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
