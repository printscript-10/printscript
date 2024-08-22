package parser.nodeBuilder

import parser.BuildResult
import parser.Failure
import parser.Success
import utils.PrintFunction
import utils.Token
import utils.TokenType

class PrintBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val openBraceIndex = position + 1
        if (tokens[openBraceIndex].type != TokenType.OPEN_BRACE) {
            return Failure(
                error = "Invalid print function format",
                position = position,
            )
        }

//        val value = when(val valueBuild = ExpressionBuilder().build(tokens, openBraceIndex)) {
//            is Success -> (valueBuild.result)
//            is Failure -> return valueBuild
//        }
        val value = StringLiteralBuilder().build(tokens, openBraceIndex + 1).result

        return Success(
            result = PrintFunction(
                value = value,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
