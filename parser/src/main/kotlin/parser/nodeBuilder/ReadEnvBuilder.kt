package parser.nodeBuilder

import utils.ReadEnv
import utils.Result
import utils.Token
import utils.TokenType

class ReadEnvBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): Result {
        val opeBraceIndex = position + 1
        val variableIndex = position + 2
        val closeBraceIndex = position + 3
        if (
            (tokens[position].type != TokenType.READ_ENV) ||
            (tokens[opeBraceIndex].type != TokenType.OPEN_BRACKET) ||
            (tokens[variableIndex].type != TokenType.IDENTIFIER) ||
            (tokens[closeBraceIndex].type != TokenType.CLOSE_BRACKET)
        ) {
            return BuildFailure("Invalid readEnv function format")
        }

        return BuildSuccess(
            result = ReadEnv(
                variable = tokens[variableIndex].value,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
