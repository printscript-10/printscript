package interpreter.nodeInterpreter

import ast.BinaryOperation
import ast.BooleanLiteral
import ast.Expression
import ast.Identifier
import ast.NumberLiteral
import ast.ReadEnv
import ast.ReadInput
import ast.StringLiteral
import ast.VariableType
import interpreter.ExpressionFailure
import interpreter.Variable
import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider
import result.Result
import kotlin.reflect.KClass

class ExpressionInterpreter(
    version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
    private val expected: VariableType,
) : ASTExpressionInterpreter<Expression> {

    private val interpreters: Map<KClass<out Expression>, ASTExpressionInterpreter<*>>

    init {
        interpreters = getInterpreters(version)
    }

    override fun execute(ast: Expression): Result {
        interpreters[ast::class]?.let {
            return (it as ASTExpressionInterpreter<Expression>).execute(ast)
        }
        return ExpressionFailure("Invalid AST node type")
    }

    private fun getInterpreters(version: String): Map<KClass<out Expression>, ASTExpressionInterpreter<*>> {
        val baseMap = mapOf(
            BinaryOperation::class to BinaryOperationInterpreter(
                version,
                variables,
                outputProvider,
                inputProvider,
                envProvider,
                expected,
            ),
            NumberLiteral::class to NumericLiteralInterpreter(),
            StringLiteral::class to StringLiteralInterpreter(),
            Identifier::class to IdentifierInterpreter(variables),
        )
        return when (version) {
            "1.0" -> baseMap
            "1.1" -> {
                baseMap.toMutableMap().apply {
                    putAll(
                        mapOf(
                            BooleanLiteral::class to BooleanLiteralInterpreter(),
                            ReadInput::class to ReadInputInterpreter(
                                version,
                                variables,
                                outputProvider,
                                inputProvider,
                                envProvider,
                                expected,
                            ),
                            ReadEnv::class to ReadEnvInterpreter(envProvider, expected),
                        ),
                    )
                }
            }
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
