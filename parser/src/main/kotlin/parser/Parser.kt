package parser

import utils.AST
import utils.Token

class Parser {

    fun buildAST(tokens: List<Token>): List<AST> {
        return ASTBuilder().build(tokens)
    }

    fun checkAST() {
    }
}
