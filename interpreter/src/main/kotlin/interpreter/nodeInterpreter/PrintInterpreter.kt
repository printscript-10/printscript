package interpreter.nodeInterpreter

import interpreter.InterpretSuccess
import interpreter.Variable
import utils.OutputProvider
import utils.PrintFunction
import utils.Result

class PrintInterpreter(
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
) : ASTDeclarationInterpreter<PrintFunction> {

    // P R I N T E R P R E T E R
    override fun execute(ast: PrintFunction): Result {
        val value = ExpressionInterpreter(variables).execute(ast.value).value

        outputProvider.print(value)
        return InterpretSuccess(variables)
    }
}
