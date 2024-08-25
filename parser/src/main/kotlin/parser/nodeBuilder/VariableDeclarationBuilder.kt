package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Token
import utils.TokenType
import utils.VariableAssignation

class VariableDeclarationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val idIndex = position + 1
        val typeIndex = position + 3
        if (
            (tokens[position].type != TokenType.VARIABLE_DECLARATOR) ||
            (tokens[idIndex].type != TokenType.IDENTIFIER) ||
            (tokens[position + 2].type != TokenType.COLON) ||
            (tokens[typeIndex].type != TokenType.TYPE)
        ) {
            return BuildFailure(
                error = "Invalid variable declaration format",
                position = position,
            )
        }

        val identifier = IdentifierBuilder().build(tokens, idIndex).result as Identifier
        // esto es igual al variableAssignation
        val expressionResult = ExpressionBuilder().build(tokens, position)
        if (expressionResult is BuildFailure) {
            return expressionResult
        }
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
