package parser.nodeBuilder

import utils.Expression
import utils.PrintFunction
import utils.Result
import utils.Token
import utils.TokenType

class PrintBuilder(private val version: String) : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): Result {
        val openBraceIndex = position + 1
        val closingBraceIndex = tokens.size - 2
        if (
            (tokens[position].type != TokenType.PRINT) ||
            (tokens[openBraceIndex].type != TokenType.OPEN_BRACKET) ||
            (tokens[closingBraceIndex].type != TokenType.CLOSE_BRACKET)
        ) {
            return BuildFailure("Invalid print function format")
        }

        val expressionTokens = tokens.subList(openBraceIndex + 1, closingBraceIndex)
        if (expressionTokens.isEmpty()) return BuildFailure("Print function cannot be empty")

        val expressionResult = ExpressionBuilder(version).build(expressionTokens, position)
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
