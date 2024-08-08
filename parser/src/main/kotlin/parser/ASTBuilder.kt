package parser

import parser.nodeBuilder.ASTNodeBuilder
import parser.nodeBuilder.IdentifierBuilder
import parser.nodeBuilder.NumericLiteralBuilder
import parser.nodeBuilder.PrintBuilder
import parser.nodeBuilder.StringLiteralBuilder
import utils.*

class ASTBuilder {

    private val builders: Map<TokenType, ASTNodeBuilder>

    init {
        builders = mapOf(
            TokenType.STRING to StringLiteralBuilder(),
            TokenType.NUMBER to NumericLiteralBuilder(),
            TokenType.IDENTIFIER to IdentifierBuilder(),
            TokenType.PRINT to PrintBuilder()
        )
    }

    fun build(tokens: List<Token>): List<AST>{
        var result = mutableListOf<AST>()
        var tokenIterator = tokens.listIterator()
        while(tokenIterator.hasNext()){
            var currentToken = tokenIterator.next()
            val builder = builders[currentToken.type]
            if(builder != null){
                val buildResult = (builder.build(tokens, tokenIterator.previousIndex()))
                if(buildResult is Success) result.add(buildResult.result)
                // agregar caso de failure

            }
            // tirar error si no encuentra builder

        }
        return result
    }


}