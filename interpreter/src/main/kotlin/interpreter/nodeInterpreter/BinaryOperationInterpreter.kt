package interpreter.nodeInterpreter

import interpreter.Variable
import utils.BinaryOperation
import utils.BinaryOperators

class BinaryOperationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTExpressionInterpreter<BinaryOperation> {

    private val binaryOperations: Map<BinaryOperators, BinaryFunction> = mapOf(
        BinaryOperators.PLUS to Sum,
        BinaryOperators.MINUS to Subtraction,
        BinaryOperators.TIMES to Multiplication,
        BinaryOperators.DIV to Division,
    )

    override fun execute(ast: BinaryOperation): Variable {
        val left = (ExpressionInterpreter(variables).execute(ast.left))
        val right = (ExpressionInterpreter(variables).execute(ast.right))

        val operation = binaryOperations[ast.operator]

        return operation!!.execute(left, right)
    }
}
