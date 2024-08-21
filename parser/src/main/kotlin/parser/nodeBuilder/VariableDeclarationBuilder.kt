package parser.nodeBuilder

import parser.BuildResult
import parser.Failure
import parser.Success
import utils.Identifier
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration

class VariableDeclarationBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val declaratorIndex = position
        val idIndex = position + 1
        val semicolonIndex = position + 2
        val typeIndex = position + 3
        if (
            (tokens[declaratorIndex].type != TokenType.VARIABLE_DECLARATOR) &&
            (tokens[idIndex].type != TokenType.IDENTIFIER) &&
            (tokens[semicolonIndex].type != TokenType.SEMICOLON) &&
            (tokens[typeIndex].type != TokenType.TYPE)
        ) {
            return Failure(
                error = "Invalid Declaration format",
                position = position,
            )
        }
        if (tokens[position + 4].type != TokenType.SEMICOLON) {
            return Failure(
                error = "Expected semicolon at line ${tokens[position + 2].position.line}",
                position = position,
            )
        }

        val identifier = IdentifierBuilder().build(tokens, idIndex).result as Identifier
        val type = TypeBuilder().build(tokens, typeIndex).result as Type

        return Success(
            result = VariableDeclaration(
                id = identifier,
                type = type,
                position = tokens[declaratorIndex].position,
                init = null,
            ),
            position = position,
        )
    }
}
