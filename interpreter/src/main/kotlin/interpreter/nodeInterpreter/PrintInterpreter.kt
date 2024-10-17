package interpreter.nodeInterpreter

import ast.PrintFunction
import ast.VariableType
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.Variable
import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider
import result.Result

class PrintInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) : ASTDeclarationInterpreter<PrintFunction> {

    // P R I N T E R P R E T E R
    override fun execute(ast: PrintFunction): Result {
        val value = ExpressionInterpreter(
            version,
            variables,
            outputProvider,
            inputProvider,
            envProvider,
            VariableType.STRING,
        ).execute(ast.value)
        if (value is ExpressionFailure) return InterpretFailure(value.error)

        outputProvider.print((value as ExpressionSuccess).value.value.toString())
        return InterpretSuccess(variables)
    }
}
