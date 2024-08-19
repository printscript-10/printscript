package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.BinaryOperation

class BinaryOperationInterpreter(private val variables: Map<String, Variable>): ASTNodeInterpreter<Number> {
    override fun execute(ast: AST): Number {
        if(ast !is BinaryOperation){
            throw Error("Womp womp")
        }
        val left = stringToNumber(ExpressionInterpreter(variables).execute(ast.left)!!)
        val right = stringToNumber(ExpressionInterpreter(variables).execute(ast.right)!!)

        // o yea hay que cambiar esto por un mapa y cambiar la logica del valor de los NumberLiterals
        return when (ast.operator) {
            "+" -> left.toDouble() + right.toDouble()
            "-" -> left.toDouble() - right.toDouble()
            "*" -> left.toDouble() * right.toDouble()
            "/" -> left.toDouble() / right.toDouble()
            else -> throw Error("Unknown operator")
        }
    }

    fun stringToNumber(input: String): Number {
        return when {
            input.contains(".") -> input.toDouble()
            else -> input.toInt()
        }
    }

}
