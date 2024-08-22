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
        val idIndex = position + 1
        val typeIndex = position + 3
        if (
            (tokens[position].type != TokenType.VARIABLE_DECLARATOR) ||
            (tokens[idIndex].type != TokenType.IDENTIFIER) ||
            (tokens[position + 2].type != TokenType.COLON) ||
            (tokens[typeIndex].type != TokenType.TYPE)
        ) {
            return Failure(
                error = "Invalid variable declaration format",
                position = position,
            )
        }

        val identifier = IdentifierBuilder().build(tokens, idIndex).result as Identifier
        val type = TypeBuilder().build(tokens, typeIndex).result as Type
//        val init = if (tokens[position + 4].type == TokenType.ASSIGN) {
//            when(val initBuild: BuildResult = ExpressionBuilder().build(tokens, position + 5)) {
//                is Success -> initBuild.result
//                is Failure -> return initBuild
//            }
//        } else null
        val init = StringLiteralBuilder().build(tokens, position + 5).result

        return Success(
            result = VariableDeclaration(
                id = identifier,
                type = type,
                init = init,
                position = tokens[position].position,
            ),
            position = position,
        )
    }
}
