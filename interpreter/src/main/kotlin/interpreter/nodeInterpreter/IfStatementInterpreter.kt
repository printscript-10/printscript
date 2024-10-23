package interpreter.nodeInterpreter

import ast.IfStatement
import ast.VariableType
import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.Interpreter
import interpreter.Variable
import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider
import result.Result

class IfStatementInterpreter(
    private val version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) : ASTDeclarationInterpreter<IfStatement> {

    override fun execute(ast: IfStatement): Result {
        val condition = ExpressionInterpreter(
            version,
            variables,
            outputProvider,
            inputProvider,
            envProvider,
            VariableType.BOOLEAN,
        ).execute(ast.condition)
        if (condition is ExpressionFailure) return condition
        var innerContext = variables

        if ((condition as ExpressionSuccess).value.value as Boolean) {
            for (thenStatement in ast.thenStatements) {
                val result = Interpreter(version, innerContext, outputProvider, inputProvider, envProvider).interpret(
                    thenStatement,
                )
                if (result is InterpretFailure) return result
                innerContext = (result as InterpretSuccess).result
            }
        } else {
            for (elseStatement in ast.elseStatements) {
                val result = Interpreter(version, innerContext, outputProvider, inputProvider, envProvider).interpret(
                    elseStatement,
                )
                if (result is InterpretFailure) return result
                innerContext = (result as InterpretSuccess).result
            }
        }
        return InterpretSuccess(updateContext(innerContext, variables))
    }

    private fun updateContext(
        innerContext: Map<String, Variable>,
        variables: Map<String, Variable>,
    ): Map<String, Variable> {
        return variables.mapValues { (key, value) ->
            innerContext[key] ?: value
        }
    }
}
