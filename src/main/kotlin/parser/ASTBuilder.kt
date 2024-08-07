package parser

import parser.astbuilder.ASTBuilder
import parser.astbuilder.StringLiteralBuilder
import utils.*

class ASTBuilder {

    private val builders: Map<TokenType, ASTBuilder>;

    init{
        builders = mapOf(
            ,
        )
    }

    fun build(tokens: List<Token>): AST{
        var tokenIterator = tokens.listIterator()
        while(tokenIterator.hasNext())
            var currentToken = tokenIterator.next()
            when(currentToken.type){
                TokenType.TYPE -> StringLiteralBuilder().build(tokens, 1)
                "NUMBER" -> NumberLiteral(
                    value = currentToken.value.toInt(),
                    position = currentToken.position
                )
            }
    }


}