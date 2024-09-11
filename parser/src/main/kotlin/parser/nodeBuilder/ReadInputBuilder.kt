package parser.nodeBuilder

import utils.Expression
import utils.ReadInput
import utils.Result
import utils.Token
import utils.TokenType

class ReadInputBuilder(private val version: String) : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): Result {
        val openBraceIndex = position + 1
        if (
            (tokens[position].type != TokenType.READ_INPUT) ||
            (tokens[openBraceIndex].type != TokenType.OPEN_BRACKET)
        ) {
            return BuildFailure("Invalid readInput function format")
        }
        val closeBraceIndex = findClosingBrace(tokens, openBraceIndex)
        if (closeBraceIndex == -1) return BuildFailure("Mismatched parentheses")

        val expressionTokens = tokens.subList(openBraceIndex + 1, closeBraceIndex)
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
            position = position,
        )
    }

    private fun findClosingBrace(tokens: List<Token>, openBraceIndex: Int): Int {
        var braceCount = 1

        for (i in openBraceIndex + 1 until tokens.size) {
            val token = tokens[i]

            if (token.type == TokenType.OPEN_BRACE) braceCount++
            else if (token.type == TokenType.CLOSE_BRACE) braceCount--

            if (braceCount == 0) return i
        }

        return -1
    }
}
