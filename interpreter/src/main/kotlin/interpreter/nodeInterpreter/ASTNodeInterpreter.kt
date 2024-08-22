package interpreter.nodeInterpreter

import utils.AST

interface ASTNodeInterpreter<T> {
    fun execute(ast: AST): T
}
