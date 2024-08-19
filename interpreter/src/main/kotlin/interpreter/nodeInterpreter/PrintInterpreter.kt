package interpreter.nodeInterpreter

import interpreter.Interpreter
import utils.AST
import utils.PrintFunction

class PrintInterpreter : ASTNodeInterpreter<Unit> {

    // P R I N T E R P R E T E R
    override fun execute(ast: AST) {
        if (ast is PrintFunction) {
            println("print")
        }
    }
}
