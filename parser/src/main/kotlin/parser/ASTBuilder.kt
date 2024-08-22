package parser

import parser.nodeBuilder.ASTNodeBuilder
import parser.nodeBuilder.PrintBuilder
import parser.nodeBuilder.VariableAssignationBuilder
import parser.nodeBuilder.VariableDeclarationBuilder
import utils.AST
import utils.Token
import utils.TokenType

class ASTBuilder {

    private val builders: Map<TokenType, ASTNodeBuilder>

    init {
        builders = mapOf(
            TokenType.VARIABLE_DECLARATOR to VariableDeclarationBuilder(),
            TokenType.IDENTIFIER to VariableAssignationBuilder(),
            TokenType.PRINT to PrintBuilder(),
        )
    }

    fun build(tokens: List<Token>): List<AST> {
        if (tokens[tokens.size - 1].type != TokenType.SEMICOLON) {
            print("Tiene q terminar con ; viste cra")
        }
        val result = mutableListOf<AST>()
        val builder = builders[tokens[0].type]
        if (builder != null) {
            val buildResult = (builder.build(tokens, 0))
            if (buildResult is Success) {
                result.add(buildResult.result)
            } else {
                print((buildResult as Failure).error)
            }
        }
        print("Invalid line start")

        return result
    }
}
