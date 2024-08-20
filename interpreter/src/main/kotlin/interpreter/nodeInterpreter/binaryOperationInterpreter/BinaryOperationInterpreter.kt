package interpreter.nodeInterpreter.binaryOperationInterpreter

import interpreter.Variable
import interpreter.nodeInterpreter.ASTNodeInterpreter
import interpreter.nodeInterpreter.ExpressionInterpreter
import utils.AST
import utils.BinaryOperation

class BinaryOperationInterpreter(private val variables: Map<String, Variable>): ASTNodeInterpreter<Variable> {

    private val binaryOperations: Map<String, BinaryFunction>;

    init {
        binaryOperations = mapOf(
            "+" to Sum(),
            "-" to Subtraction(),
            "*" to Multiplication(),
            "/" to Division()
        )
    }

    override fun execute(ast: AST): Variable {
        if(ast !is BinaryOperation){
            throw Error("Womp womp")
        }
        val left = (ExpressionInterpreter(variables).execute(ast.left)!!)
        val right = (ExpressionInterpreter(variables).execute(ast.right)!!)

        val operation = binaryOperations[ast.operator]

        if(operation == null){
            throw Error("${ast.operator} is not a valid operator")
        }
        return operation.execute(left, right)
    }
}
