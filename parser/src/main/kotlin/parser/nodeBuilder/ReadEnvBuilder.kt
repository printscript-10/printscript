package parser.nodeBuilder

import ast.ReadEnv
import result.Result
import token.Token
import token.TokenType

class ReadEnvBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): Result {
        val opeBracketIndex = position + 1
        val variableIndex = position + 2
        val closeBracketIndex = position + 3
        if (
            (tokens[position].type != TokenType.READ_ENV) ||
            (tokens[opeBracketIndex].type != TokenType.OPEN_BRACKET) ||
            (tokens[variableIndex].type != TokenType.STRING) ||
            (tokens[closeBracketIndex].type != TokenType.CLOSE_BRACKET)
        ) {
            return BuildFailure("Invalid readEnv function format")
        }

        return BuildSuccess(
            result = ReadEnv(
                variable = tokens[variableIndex].value,
                position = tokens[position].position,
            ),
            position = closeBracketIndex,
        )
    }
}
