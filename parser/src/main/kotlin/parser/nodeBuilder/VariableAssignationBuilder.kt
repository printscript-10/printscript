package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Result
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class VariableAssignationBuilder(private val version: String) : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): Result {
        val assignIndex = position + 1
        if (
            (tokens[position].type != TokenType.IDENTIFIER) ||
            (tokens[assignIndex].type != TokenType.ASSIGN)
        ) {
            return BuildFailure("Invalid variable assignation format")
        }

        val identifier = IdentifierBuilder().build(tokens, position).result as Identifier
        val expressionTokens = tokens.subList(assignIndex + 1, tokens.size - 1)
        if (expressionTokens.isEmpty()) return BuildFailure("Assignation cannot be empty")

        val expressionResult = ExpressionBuilder(version).build(expressionTokens, position)
        if (expressionResult is BuildFailure) return expressionResult

        val value = (expressionResult as BuildSuccess).result as Expression
        return BuildSuccess(
            result = VariableAssignation(
                id = identifier,
                value = value,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
