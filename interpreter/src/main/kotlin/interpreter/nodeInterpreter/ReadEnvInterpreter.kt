package interpreter.nodeInterpreter

import ast.ReadEnv
import ast.VariableType
import interpreter.ExpressionFailure
import provider.EnvProvider
import result.Result

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
