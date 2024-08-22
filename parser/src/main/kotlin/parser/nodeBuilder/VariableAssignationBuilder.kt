package parser.nodeBuilder

import parser.BuildResult
import parser.Failure
import parser.Success
import utils.Identifier
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class VariableAssignationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): BuildResult {
        if (
            (tokens[position].type != TokenType.IDENTIFIER) ||
            (tokens[position + 1].type != TokenType.ASSIGN)
        ) {
            return Failure(
                error = "Invalid variable assignation format",
                position = position,
            )
        }

        val identifier = IdentifierBuilder().build(tokens, position).result as Identifier
//        val value = when(val valueBuild = ExpressionBuilder().build(tokens, position + 2)) {
//            is Success -> valueBuild.result
//            is Failure -> return valueBuild
//        }
        val value = StringLiteralBuilder().build(tokens, position + 2).result

        return Success(
            result = VariableAssignation(
                id = identifier,
                value = value,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
