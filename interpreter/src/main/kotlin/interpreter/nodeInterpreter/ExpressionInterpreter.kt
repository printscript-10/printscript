package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.BinaryOperation
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral

class ExpressionInterpreter(private val variables: Map<String, Variable>): ASTNodeInterpreter<String?> {
    override fun execute(ast: AST): String? {
        when(ast){
            // puaj otro casteo a string de un Number manu hacete cargo
            is BinaryOperation -> return BinaryOperationInterpreter(variables).execute(ast).toString()
            is NumberLiteral -> return NumericLiteralInterpreter().execute(ast)
            is StringLiteral -> return StringLiteralInterpreter().execute(ast)
            is Identifier -> return IdentifierInterpreter(variables).execute(ast)
            else -> return null
        }
    }
}
