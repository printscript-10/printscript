package interpreter.nodeInterpreter

import interpreter.InterpretResult
import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import utils.VariableAssignation

class VariableAssignationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTDeclarationInterpreter<VariableAssignation> {
    override fun execute(ast: VariableAssignation): InterpretResult {
        val currentVariable = variables[ast.id.name]!!

        val result = when (currentVariable) {
            is NumericVariable -> {
                val value = ExpressionInterpreter(variables).execute(ast.value).value as Number
                NumericVariable(value)
            }
            is StringVariable -> {
                val value = ExpressionInterpreter(variables).execute(ast.value).value as String
                StringVariable(value)
            }
        }

        return InterpretSuccess(
            variables.toMutableMap().apply {
                this[ast.id.name] = result
            }.toMap(),
        )
    }
}
