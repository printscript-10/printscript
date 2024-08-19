package interpreter

import interpreter.nodeInterpreter.NumericLiteralInterpreter
import interpreter.nodeInterpreter.PrintInterpreter
import interpreter.nodeInterpreter.StringLiteralInterpreter
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import utils.*

class Interpreter {

    private val variables: Map<String, Variable> = mapOf()

    fun interpret(ast: AST){
        //match interpreters to ast type
        when(ast){
            is BinaryOperator -> TODO()
            is Identifier -> TODO()
            is NumberLiteral -> NumericLiteralInterpreter().execute(ast)
            is StringLiteral -> StringLiteralInterpreter().execute(ast)
            is PrintFunction -> PrintInterpreter().execute(ast)
            is Type -> TODO()
            is VariableDeclaration -> VariableDeclarationInterpreter(variables).execute(ast)
        }
    }
}
