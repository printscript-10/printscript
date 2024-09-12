package interpreter.nodeInterpreter

import interpreter.BooleanVariable
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import utils.EnvProvider
import utils.InputProvider
import utils.OutputProvider
import utils.Result
import utils.VariableAssignation
import utils.VariableType

class VariableAssignationInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) : ASTDeclarationInterpreter<VariableAssignation> {
    override fun execute(ast: VariableAssignation): Result {
        val currentVariable = variables[ast.id.name]!!

        if (currentVariable.isFinal) return InterpretFailure("${ast.id.name} cannot be reassigned")

        val expectedType = when (currentVariable) {
            is NumericVariable -> VariableType.NUMBER
            is StringVariable -> VariableType.STRING
            is BooleanVariable -> VariableType.BOOLEAN
        }

        val value = ExpressionInterpreter(
            version,
            variables,
            outputProvider,
            inputProvider,
            envProvider,
            expectedType,
        ).execute(ast.value)

        if (value is ExpressionFailure) return InterpretFailure(value.error)
        val variableValue = (value as ExpressionSuccess).value.value

        val result = when (currentVariable) {
            is NumericVariable -> {
                NumericVariable(variableValue as Number, false)
            }
            is StringVariable -> {
                StringVariable(variableValue as String, false)
            }
            is BooleanVariable -> {
                BooleanVariable(variableValue as Boolean, false)
            }
        }

        return InterpretSuccess(
            variables.toMutableMap().apply {
                this[ast.id.name] = result
            }.toMap(),
        )
    }
}
