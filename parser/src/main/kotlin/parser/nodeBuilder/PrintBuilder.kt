package parser.nodeBuilder

import ast.Expression
import ast.PrintFunction
import result.Result
import token.Token
import token.TokenType

class PrintBuilder(private val version: String) : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): Result {
        val openBracketIndex = position + 1
        val closingBracketIndex = findClosingBracket(tokens, openBracketIndex)
        if (closingBracketIndex == -1) return BuildFailure("Mismatched parentheses")

        if (
            (tokens[position].type != TokenType.PRINT) ||
            (tokens[openBracketIndex].type != TokenType.OPEN_BRACKET) ||
            (closingBracketIndex != tokens.size - 2)
        ) {
            return BuildFailure("Invalid print function format")
        }

        val expressionTokens = tokens.subList(openBracketIndex + 1, closingBracketIndex)
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

    private fun findClosingBracket(tokens: List<Token>, openBracketIndex: Int): Int {
        var bracketCount = 1

        for (i in openBracketIndex + 1 until tokens.size) {
            val token = tokens[i]

            if (token.type == TokenType.OPEN_BRACKET) {
                bracketCount++
            } else if (token.type == TokenType.CLOSE_BRACKET) bracketCount--

            if (bracketCount == 0) return i
        }

        return -1
    }
}
