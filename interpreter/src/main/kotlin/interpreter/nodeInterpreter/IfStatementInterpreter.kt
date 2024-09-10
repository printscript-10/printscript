package interpreter.nodeInterpreter

import interpreter.InterpretFailure
import interpreter.InterpretSuccess
import interpreter.Interpreter
import interpreter.Variable
import utils.IfStatement
import utils.OutputProvider
import utils.Result

class IfStatementInterpreter(
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
) : ASTDeclarationInterpreter<IfStatement> {

    override fun execute(ast: IfStatement): Result {
        var innerContext = variables
        val condition = ExpressionInterpreter(variables).execute(ast.condition).value

        if (condition as Boolean) {
            for (thenStatement in ast.thenStatements) {
                val result = Interpreter(innerContext, outputProvider).interpret(thenStatement)
                if (result is InterpretFailure) return result
                innerContext = (result as InterpretSuccess).result
            }
        } else {
            for (elseStatement in ast.elseStatements) {
                val result = Interpreter(innerContext, outputProvider).interpret(elseStatement)
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
