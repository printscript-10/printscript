package interpreter.nodeInterpreter

import interpreter.BooleanVariable
import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import utils.Result
import utils.VariableAssignation

class VariableAssignationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTDeclarationInterpreter<VariableAssignation> {
    override fun execute(ast: VariableAssignation): Result {
        val currentVariable = variables[ast.id.name]!!

        val result = when (currentVariable) {
            is NumericVariable -> {
                val value = ExpressionInterpreter(variables).execute(ast.value).value as Number
                NumericVariable(value, false)
            }
            is StringVariable -> {
                val value = ExpressionInterpreter(variables).execute(ast.value).value as String
                StringVariable(value, false)
            }
            is BooleanVariable -> {
                val value = ExpressionInterpreter(variables).execute(ast.value).value as Boolean
                BooleanVariable(value, false)
            }
        }

        return InterpretSuccess(
            variables.toMutableMap().apply {
                this[ast.id.name] = result
            }.toMap(),
        )
    }
}
