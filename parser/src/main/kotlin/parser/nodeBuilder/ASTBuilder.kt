package parser.nodeBuilder

import utils.Token
import utils.TokenType

class ASTBuilder {

    private val builders: Map<TokenType, ASTNodeBuilder> = mapOf(
        TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(),
        TokenType.IDENTIFIER to VariableAssignationBuilder(),
        TokenType.PRINT to PrintBuilder(),
    )

    fun build(tokens: List<Token>): BuildResult {
        if (tokens[tokens.size - 1].type != TokenType.SEMICOLON) {
            print("Tiene q terminar con ; viste cra")
        }
        val builder = builders[tokens[0].type]
        if (builder != null) {
            return (builder.build(tokens, 0))
        }

        return BuildFailure("Unrecognized token", tokens.first().position.line)
    }
}
