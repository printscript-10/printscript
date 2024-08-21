package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.VariableDeclaration

class VariableDeclarationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTNodeInterpreter<Map<String, Variable>> {

    override fun execute(ast: AST): Map<String, Variable> {
        if (ast !is VariableDeclaration) {
            return variables
        }
        var result = Variable(
            type = ast.type.name,
            value = null,
        )
        if (ast.init != null) {
            result = Variable(
                type = ast.type.name,
                // double use of !!, this sucks
                value = ExpressionInterpreter(variables).execute(ast.init!!)!!.value,
            )
        }

        val updatedVariables = variables.toMutableMap()

        updatedVariables[ast.id.name] = result

        return updatedVariables.toMap()
    }
}
