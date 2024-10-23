package interpreter.nodeInterpreter

import ast.VariableDeclaration
import ast.VariableType
import interpreter.BooleanVariable
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider
import result.Result

class VariableDeclarationInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) : ASTDeclarationInterpreter<VariableDeclaration> {
    override fun execute(ast: VariableDeclaration): Result {
        val initResult = ast.init?.let {
            ExpressionInterpreter(
                version,
                variables,
                outputProvider,
                inputProvider,
                envProvider,
                ast.type.name,
            ).execute(it)
        }

        if (initResult is ExpressionFailure) return InterpretFailure(initResult.error)
        val initValue = if (initResult != null) (initResult as ExpressionSuccess).value.value else null

        val result = when (ast.type.name) {
            VariableType.NUMBER -> {
                NumericVariable(initValue as? Number ?: null, ast.isFinal)
            }
            VariableType.STRING -> {
                StringVariable(initValue as? String ?: null, ast.isFinal)
            }
            VariableType.BOOLEAN -> {
                BooleanVariable(initValue as? Boolean ?: null, ast.isFinal)
            }
            else -> return InterpretFailure("Invalid expected variable type")
        }

        return InterpretSuccess(
            variables.toMutableMap().apply {
                this[ast.id.name] = result
            }.toMap(),
        )
    }
}
