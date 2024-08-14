package interpreter

import utils.AST
import utils.PrintFunction

class PrintInterpreter : Interpreter {

    // P R I N T E R P R E T E R
    override fun execute(ast: AST) {
        if (ast is PrintFunction) {
            println("print")
        }
    }
}
