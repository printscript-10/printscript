package interpreter

import interpreter.nodeInterpreter.PrintInterpreter
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import utils.AST
import utils.PrintFunction
import utils.VariableDeclaration

class Interpreter {
    private var variables: Map<String, Variable> = mapOf()

    fun interpret(ast: AST) {
        when (ast) {
            is PrintFunction -> PrintInterpreter(variables).execute(ast)
            is VariableDeclaration -> variables = VariableDeclarationInterpreter(variables).execute(ast)
            // is Assignation -> AssignationInterpreter(variables).execute(ast)
            else -> throw Error("Forget about it cuh")
        }
    }
}
