package interpreter

import utils.AST

interface Interpreter {
    fun execute(ast: AST)
}