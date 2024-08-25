package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.PrintFunction

class PrintInterpreter(private val variables: Map<String, Variable>) : ASTNodeInterpreter<Unit> {

    // P R I N T E R P R E T E R
    override fun execute(ast: AST) {
        if (ast !is PrintFunction) {
            throw Error("Expected printAST at ${ast.position}")
        }
        // sacar el !! q es un asco y usar results (mas results chii)
        val value = ExpressionInterpreter(variables).execute(ast.value)!!.value

        println(value)
        return
    }
}
