package interpreter

import interpreter.nodeInterpreter.IfStatementInterpreter
import interpreter.nodeInterpreter.PrintInterpreter
import interpreter.nodeInterpreter.VariableAssignationInterpreter
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import utils.AST
import utils.IfStatement
import utils.OutputProvider
import utils.PrintFunction
import utils.Result
import utils.VariableAssignation
import utils.VariableDeclaration

class Interpreter(
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider
) {

    fun interpret(ast: AST): Result {
        return when (ast) {
            is PrintFunction -> PrintInterpreter(variables, outputProvider).execute(ast)
            is VariableDeclaration -> VariableDeclarationInterpreter(variables).execute(ast)
            is VariableAssignation -> VariableAssignationInterpreter(variables).execute(ast)
            is IfStatement -> IfStatementInterpreter(variables, outputProvider).execute(ast)
            else -> return InterpretFailure("Invalid AST node type")
        }
    }
}
