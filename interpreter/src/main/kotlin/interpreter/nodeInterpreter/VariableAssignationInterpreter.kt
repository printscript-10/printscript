package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.VariableAssignation
import utils.VariableDeclaration

class VariableAssignationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTNodeInterpreter<Map<String, Variable>> {
    override fun execute(ast: AST): Map<String, Variable> {
        if(ast !is VariableAssignation){
            return variables
        }
        val currentVariable = variables[ast.id.name]
        if(currentVariable == null){
            // TODO: cambiar esto por result
            return variables
        }
        val result = Variable(
            type = currentVariable.type,
            value = ExpressionInterpreter(variables).execute(ast.value)!!.value,
        )

        val updatedVariables = variables.toMutableMap()
        updatedVariables[ast.id.name] = result

        return updatedVariables.toMap()
    }

}
