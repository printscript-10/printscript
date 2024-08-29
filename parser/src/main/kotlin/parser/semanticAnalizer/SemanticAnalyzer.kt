package parser.semanticAnalizer

import utils.AST
import utils.PrintFunction
import utils.VariableAssignation
import utils.VariableDeclaration
import utils.VariableType

class SemanticAnalyzer(private val variables: Map<String, VariableType>) {

    fun analyze(ast: AST): SemanticAnalyzerResult {
        return when (ast) {
            is VariableDeclaration -> checkVariableDeclaration(ast)
            is PrintFunction -> checkPrintFunction(ast)
            is VariableAssignation -> checkVariableAssignation(ast)
            else -> Failure("Declaration must be a variable declaration, assignation or printfunction")
        }
    }

    private fun checkPrintFunction(printFunction: PrintFunction): SemanticAnalyzerResult {
        val result = printFunction.value.accept(TypeVisitor(variables))
        if (result is Failure) return result
        return Success(variables)
    }

    private fun checkVariableDeclaration(ast: VariableDeclaration): SemanticAnalyzerResult {
        val type = ast.type.name
        if (ast.init == null) return Success(variables)

        val expressionType = ast.init!!.accept(TypeVisitor(variables))
        if (expressionType is VisitSuccess && type == expressionType.type) {
            return Success(
                variables.toMutableMap().apply {
                    this[ast.id.name] = ast.type.name
                }.toMap(),
            )
        }

        if (expressionType is VisitSuccess) return Failure("Cannot assign a ${expressionType.type} to a ${type.name}")

        return expressionType
    }

    private fun checkVariableAssignation(ast: VariableAssignation): SemanticAnalyzerResult {
        val type = variables[ast.id.name] ?: return Failure("${ast.id.name} hasnt been declared")

        val expressionType = ast.value.accept(TypeVisitor(variables))
        if (expressionType is VisitSuccess && type == expressionType.type) return Success(variables)

        if (expressionType is VisitSuccess) return Failure("Cannot assign a ${expressionType.type} to a $type")

        return expressionType
    }
}
