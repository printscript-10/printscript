package interpreter.nodeInterpreter

import interpreter.BooleanVariable
import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import utils.Result
import utils.VariableDeclaration
import utils.VariableType

class VariableDeclarationInterpreter(
    private val variables: Map<String, Variable>,
) : ASTDeclarationInterpreter<VariableDeclaration> {
    override fun execute(ast: VariableDeclaration): Result {
        val initValue = ast.init?.let {
            ExpressionInterpreter(variables).execute(it).value
        }

        val result = when (ast.type.name) {
            VariableType.NUMBER -> {
                NumericVariable(initValue as Number, false)
            }
            VariableType.STRING -> {
                StringVariable(initValue as String, false)
            }
            VariableType.BOOLEAN -> {
                BooleanVariable(initValue as Boolean, false)
            }
        }

        return InterpretSuccess(
            variables.toMutableMap().apply {
                this[ast.id.name] = result
            }.toMap(),
        )
    }
}
