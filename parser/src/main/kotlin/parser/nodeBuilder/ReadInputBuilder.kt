package parser.nodeBuilder

import ast.Expression
import ast.ReadInput
import result.Result
import token.Token
import token.TokenType

class ReadInputBuilder(private val version: String) : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): Result {
        val openBracketIndex = position + 1
        if (
            (tokens[position].type != TokenType.READ_INPUT) ||
            (tokens[openBracketIndex].type != TokenType.OPEN_BRACKET)
        ) {
            return BuildFailure("Invalid readInput function format")
        }
        val closeBracketIndex = findClosingBracket(tokens, openBracketIndex)
        if (closeBracketIndex == -1) return BuildFailure("Mismatched parentheses")

        val expressionTokens = tokens.subList(openBracketIndex + 1, closeBracketIndex)
        if (expressionTokens.isEmpty()) {
            return BuildFailure("ReadInput function cannot be empty")
        }

        val expressionResult = ExpressionBuilder(version).build(expressionTokens, position)
        if (expressionResult is BuildFailure) return expressionResult

        return BuildSuccess(
            result = ReadInput(
                message = (expressionResult as BuildSuccess).result as Expression,
                position = tokens[position].position,
            ),
            position = closeBracketIndex,
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
