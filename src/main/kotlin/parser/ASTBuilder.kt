package parser

import utils.Token
import utils.AST
import utils.NumberLiteral
import utils.StringLiteral
import utils.VariableDeclaration

class ASTBuilder {

    fun build(tokens: List<Token>): AST{
        val nodes = nodeBuilder(tokens)
        var tokenIterator = tokens.listIterator()
        while(tokenIterator.hasNext())
            var currentToken = tokenIterator.next()
            when(currentToken.type){
                "LET" -> VariableDeclaration(
                    id= tokenIterator.next()
                )
            }
    }

    private fun nodeBuilder(tokens: List<Token>): List<AST>{
        var result = mutableListOf<AST>()
        for(token in tokens){
            val node = tokenParser(token)
            result.add(node)
        }
        return result
    }

}