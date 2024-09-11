package parser.nodeBuilder

import utils.Expression
import utils.Identifier
import utils.Result
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration
// manuel
class VariableDeclarationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): Result {
        val idIndex = position + 1
        val typeIndex = position + 3
        val assignIndex = position + 4
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
        val isFinal = if (tokens[position].value == "let") false else true
        val type = TypeBuilder().build(tokens, typeIndex).result as Type
        var value: Expression? = null

        val expressionTokens =
            if (tokens[assignIndex].type == TokenType.ASSIGN) {
                tokens.subList(assignIndex + 1, tokens.size - 1)
            } else {
                listOf()
            }
        if (tokens[assignIndex].type == TokenType.ASSIGN && expressionTokens.isEmpty()) {
            return BuildFailure(
                error = "Expression cannot be empty",
                position = position,
            )
        }

        if (expressionTokens.isNotEmpty()) {
            val expressionResult = ExpressionBuilder().build(expressionTokens, position)
            if (expressionResult is BuildFailure) return expressionResult
            value = (expressionResult as BuildSuccess).result as Expression
        } else if (isFinal) {
            return BuildFailure("Const declaration cannot have empty value", position)
        }

        return BuildSuccess(
            result = VariableDeclaration(
                id = identifier,
                init = value,
                type = type,
                isFinal = isFinal,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
