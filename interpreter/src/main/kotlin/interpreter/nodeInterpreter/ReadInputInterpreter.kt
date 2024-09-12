package interpreter.nodeInterpreter

import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.Variable
import utils.EnvProvider
import utils.InputProvider
import utils.OutputProvider
import utils.ReadInput
import utils.Result
import utils.VariableType

class ReadInputInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val expected: VariableType,
) : ASTExpressionInterpreter<ReadInput> {
    override fun execute(ast: ReadInput): Result {
        val message = ExpressionInterpreter(
            version,
            variables,
            outputProvider,
            inputProvider,
            envProvider,
            VariableType.STRING,
        ).execute(ast.message)
        if (message is ExpressionFailure) return ExpressionFailure(message.error)

        val output = (message as ExpressionSuccess).value.value.toString()
        outputProvider.print(output)
        val result = inputProvider.readInput(output)

        if (result.isNullOrBlank()) return ExpressionFailure("No input provided")

        return getVariable(expected, result)
    }
}
