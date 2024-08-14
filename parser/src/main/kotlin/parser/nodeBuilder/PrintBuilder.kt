package parser.nodeBuilder

import parser.ASTBuilder
import parser.BuildResult
import parser.Failure
import parser.Success
import utils.Token
import utils.TokenType

class PrintBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        if (tokens[position + 1].type != TokenType.OPEN_BRACE) {
            return Failure(
                error = "Expected opening brace at line ${tokens[position + 1].position.line}",
                position = position,
            )
        }

        val closingBracePos = findClosingBracePosition(tokens, position + 1)
        if (closingBracePos == -1) {
            return Failure(
                error = "Expected closing brace at line ${tokens[position + 1].position.line}",
                position = position,
            )
        }

        val innerTokens = tokens.subList(position + 2, closingBracePos)
        return Success(
            result = ASTBuilder().build(innerTokens).first(),
            position = position,
        )
    }

    private fun findClosingBracePosition(tokens: List<Token>, openingBracePosition: Int): Int {
        var braceBalance = 1
        var currentPosition = openingBracePosition + 1

        while (currentPosition < tokens.size) {
            when (tokens[currentPosition].type) {
                TokenType.OPEN_BRACE -> braceBalance++
                TokenType.CLOSE_BRACE -> braceBalance--
                else -> continue
            }
            if (braceBalance == 0) {
                return currentPosition
            }

            currentPosition++
        }
        return -1
    }
}
