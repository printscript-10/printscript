package parser.semanticAnalizer

import ast.AST
import ast.IfStatement
import ast.PrintFunction
import ast.VariableAssignation
import ast.VariableDeclaration
import ast.VariableType

class SemanticAnalyzer(private val variables: Map<String, VariableType>) {

    fun analyze(ast: AST): SemanticAnalyzerResult {
        return when (ast) {
            is VariableDeclaration -> checkVariableDeclaration(ast)
            is PrintFunction -> checkPrintFunction(ast)
            is VariableAssignation -> checkVariableAssignation(ast)
            is IfStatement -> checkIfStatement(ast)
            else -> Failure("Declaration must be a variable declaration, assignation or printfunction")
        }
    }

    private fun checkPrintFunction(printFunction: PrintFunction): SemanticAnalyzerResult {
        val result = printFunction.value.accept(TypeVisitor(variables))
        if (result is VisitSuccess) return Success(variables)
        return result
    }

    private fun checkVariableDeclaration(ast: VariableDeclaration): SemanticAnalyzerResult {
        val type = ast.type.name
        if (ast.init == null) {
            return Success(
                variables.toMutableMap().apply {
                    this[ast.id.name] = ast.type.name
                }.toMap(),
            )
        }
        if (variables.containsKey(ast.id.name)) return Failure("${ast.id.name} has already been declared")

        val expressionType = ast.init!!.accept(TypeVisitor(variables))
        if (
            expressionType is VisitSuccess &&
            (type == expressionType.type || VariableType.UNKNOWN == expressionType.type)
        ) {
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
        if (
            expressionType is VisitSuccess &&
            (type == expressionType.type || VariableType.UNKNOWN == expressionType.type)
        ) {
            return Success(
                variables,
            )
        }

        if (expressionType is VisitSuccess) return Failure("Cannot assign a ${expressionType.type} to a $type")

        return expressionType
    }

    private fun checkIfStatement(ast: IfStatement): SemanticAnalyzerResult {
        var innerContext = variables
        val conditionType = ast.condition.accept(TypeVisitor(innerContext))

        if (conditionType is Failure) return conditionType
        if (conditionType is VisitSuccess && conditionType.type != VariableType.BOOLEAN) {
            return Failure(
                "Boolean expression expected in if statement",
            )
        }

        for (thenStatement in ast.thenStatements) {
            val result = SemanticAnalyzer(innerContext).analyze(thenStatement)
            if (result is Failure) return result
            innerContext = (result as Success).variables
        }
        // This is to reset the context, meaning If block and Else block have separated contexts
        innerContext = variables
        for (elseStatement in ast.elseStatements) {
            val result = SemanticAnalyzer(innerContext).analyze(elseStatement)
            if (result is Failure) return result
            innerContext = (result as Success).variables
        }
        return Success(variables)
    }
}
