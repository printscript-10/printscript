package interpreter.nodeInterpreter

import interpreter.ExpressionFailure
import utils.EnvProvider
import utils.ReadEnv
import utils.Result
import utils.VariableType

class ReadEnvInterpreter(
    private val envProvider: EnvProvider,
    private val expected: VariableType,
) : ASTExpressionInterpreter<ReadEnv> {
    override fun execute(ast: ReadEnv): Result {
        val result = envProvider.getEnv(ast.variable)

        if (result.isNullOrBlank()) return ExpressionFailure("Variable ${ast.variable} not found in environment")

        return getVariable(expected, result)
    }
}
