package interpreter

import interpreter.nodeInterpreter.ASTDeclarationInterpreter
import interpreter.nodeInterpreter.IfStatementInterpreter
import interpreter.nodeInterpreter.PrintInterpreter
import interpreter.nodeInterpreter.VariableAssignationInterpreter
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import utils.AST
import utils.Declaration
import utils.EnvProvider
import utils.IfStatement
import utils.InputProvider
import utils.OutputProvider
import utils.PrintFunction
import utils.Result
import utils.VariableAssignation
import utils.VariableDeclaration
import kotlin.reflect.KClass

class Interpreter(
    version: String,
    private val variables: Map<String, Variable>,
    private val outputProvider: OutputProvider,
    private val inputProvider: InputProvider,
    private val envProvider: EnvProvider,
) {

    private val interpreters: Map<KClass<out Declaration>, ASTDeclarationInterpreter<*>>

    init {
        interpreters = getInterpreters(version)
    }

    fun interpret(ast: AST): Result {
        interpreters[ast::class]?.let {
            return (it as ASTDeclarationInterpreter<Declaration>).execute(ast as Declaration)
        }
        return InterpretFailure("Invalid AST node type")
    }

    private fun getInterpreters(version: String): Map<KClass<out Declaration>, ASTDeclarationInterpreter<*>> {
        val baseMap = mapOf(
            PrintFunction::class to PrintInterpreter(version, variables, outputProvider, inputProvider, envProvider),
            VariableDeclaration::class to VariableDeclarationInterpreter(
                version,
                variables,
                outputProvider,
                inputProvider,
                envProvider,
            ),
            VariableAssignation::class to VariableAssignationInterpreter(
                version,
                variables,
                outputProvider,
                inputProvider,
                envProvider,
            ),
        )
        return when (version) {
            "1.0" -> baseMap
            "1.1" -> {
                baseMap.toMutableMap().apply {
                    putAll(
                        mapOf(
                            IfStatement::class to IfStatementInterpreter(
                                version,
                                variables,
                                outputProvider,
                                inputProvider,
                                envProvider,
                            ),
                        ),
                    )
                }
            }
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
