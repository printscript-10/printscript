package interpreter.nodeInterpreter

import interpreter.InterpretResult
import interpreter.InterpretSuccess
import interpreter.Variable
import utils.PrintFunction

class PrintInterpreter(private val variables: Map<String, Variable>) : ASTDeclarationInterpreter<PrintFunction> {

    // P R I N T E R P R E T E R
    override fun execute(ast: PrintFunction): InterpretResult {
        val value = ExpressionInterpreter(variables).execute(ast.value).value

        println(value)
        return InterpretSuccess(variables)
    }
}
