package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral

class ExpressionInterpreter(private val variables: Map<String, Variable>): ASTNodeInterpreter<String?> {
    override fun execute(ast: AST): String? {
        when(ast){
            is NumberLiteral -> return NumericLiteralInterpreter().execute(ast)
            is StringLiteral -> return StringLiteralInterpreter().execute(ast)
            is Identifier -> return IdentifierInterpreter(variables).execute(ast)
            // add binary operation interpreter
            else -> return null
        }
    }
}
