package interpreter.nodeInterpreter

import interpreter.Variable
import interpreter.nodeInterpreter.binaryOperationInterpreter.BinaryOperationInterpreter
import utils.AST
import utils.BinaryOperation
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral

class ExpressionInterpreter(private val variables: Map<String, Variable>) : ASTNodeInterpreter<Variable?> {
    override fun execute(ast: AST): Variable? {
        when (ast) {
            is BinaryOperation -> return BinaryOperationInterpreter(variables).execute(ast)
            is NumberLiteral -> return NumericLiteralInterpreter().execute(ast)
            is StringLiteral -> return StringLiteralInterpreter().execute(ast)
            is Identifier -> return IdentifierInterpreter(variables).execute(ast)
            else -> return null
        }
    }
}
