package interpreter.nodeInterpreter

import interpreter.Variable
import utils.BinaryOperation
import utils.BooleanLiteral
import utils.Expression
import utils.Identifier
import utils.NumberLiteral
import utils.ReadEnv
import utils.ReadInput
import utils.StringLiteral

class ExpressionInterpreter(private val variables: Map<String, Variable>) : ASTExpressionInterpreter<Expression> {
    override fun execute(ast: Expression): Variable {
        return when (ast) {
            is BinaryOperation -> BinaryOperationInterpreter(variables).execute(ast)
            is NumberLiteral -> NumericLiteralInterpreter().execute(ast)
            is StringLiteral -> StringLiteralInterpreter().execute(ast)
            is Identifier -> IdentifierInterpreter(variables).execute(ast)
            is BooleanLiteral -> BooleanLiteralInterpreter().execute(ast)
            is ReadInput -> TODO()
            is ReadEnv -> TODO()
        }
    }
}
