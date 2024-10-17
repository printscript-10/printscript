package interpreter.nodeInterpreter

import ast.BinaryOperation
import ast.BinaryOperators
import ast.VariableType
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.Variable
import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider
import result.Result

class BinaryOperationInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val expected: VariableType,
) : ASTExpressionInterpreter<BinaryOperation> {

    private val binaryOperations: Map<BinaryOperators, BinaryFunction> = mapOf(
        BinaryOperators.PLUS to Sum,
        BinaryOperators.MINUS to Subtraction,
        BinaryOperators.TIMES to Multiplication,
        BinaryOperators.DIV to Division,
    )

    override fun execute(ast: BinaryOperation): Result {
        val left = (
            ExpressionInterpreter(version, variables, outputProvider, inputProvider, envProvider, expected).execute(
                ast.left,
            )
            )
        val right = (
            ExpressionInterpreter(version, variables, outputProvider, inputProvider, envProvider, expected).execute(
                ast.right,
            )
            )

        val operation = binaryOperations[ast.operator]

        if (left is ExpressionSuccess && right is ExpressionSuccess) {
            return ExpressionSuccess(operation!!.execute(left.value, right.value))
        }

        return if (left is ExpressionFailure) {
            left
        } else {
            right
        }
    }
}
