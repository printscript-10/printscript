package interpreter

import interpreter.nodeInterpreter.PrintInterpreter
import interpreter.nodeInterpreter.VariableAssignationInterpreter
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import utils.AST
import utils.PrintFunction
import utils.VariableAssignation
import utils.VariableDeclaration

class Interpreter(private var variables: Map<String, Variable>) {

    fun interpret(ast: AST): InterpretResult {
        return when (ast) {
            is PrintFunction -> PrintInterpreter(variables).execute(ast)
            is VariableDeclaration -> VariableDeclarationInterpreter(variables).execute(ast)
            is VariableAssignation -> VariableAssignationInterpreter(variables).execute(ast)
            else -> return InterpretFailure("Invalid AST node type")
        }
    }
}
