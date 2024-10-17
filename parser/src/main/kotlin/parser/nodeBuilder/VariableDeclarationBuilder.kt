package parser.nodeBuilder

import ast.Expression
import ast.Identifier
import ast.Type
import ast.VariableDeclaration
import result.Result
import token.Token
import token.TokenType

class VariableDeclarationBuilder(private val version: String) : ASTNodeBuilder {
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
            return BuildFailure("Invalid variable declaration format")
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
            return BuildFailure("Expression cannot be empty")
        }

        if (expressionTokens.isNotEmpty()) {
            val expressionResult = ExpressionBuilder(version).build(expressionTokens, position)
            if (expressionResult is BuildFailure) return expressionResult
            value = (expressionResult as BuildSuccess).result as Expression
        } else if (isFinal) {
            return BuildFailure("Const declaration cannot have empty value")
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
