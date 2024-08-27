package parser.semanticAnalizer

import utils.AST
import utils.PrintFunction
import utils.VariableAssignation
import utils.VariableDeclaration

class SemanticAnalizer {

    var variables: MutableMap<String, String> = mutableMapOf()

    fun analize(ast: AST): SemanticAnalizerResult {
        when (ast) {
            is VariableDeclaration -> return checkVariableDeclaration(ast)
            is PrintFunction -> return checkPrintFunction(ast)
            is VariableAssignation -> return checkVariableAssignation(ast)
            else -> return Failure("Declaration must be a variable declaration, assignation or printfunction")
        }
    }

    private fun checkPrintFunction(printFunction: PrintFunction): SemanticAnalizerResult {
        return printFunction.value.accept(TypeVisitor(variables))
    }

    private fun checkVariableDeclaration(ast: VariableDeclaration): SemanticAnalizerResult {
        val type = ast.type
        if (ast.init == null) {
            return Success("null")
        }
        val expressionType = ast.init!!.accept(TypeVisitor(variables))
        if (expressionType is Success && type.name == expressionType.type) {
            variables[ast.id.name] = ast.type.name
            return Success(type.name)
        }
        if (expressionType is Success) {
            return Failure("Cannot assign a ${expressionType.type} to a ${type.name}")
        }
        return expressionType
    }

    private fun checkVariableAssignation(ast: VariableAssignation): SemanticAnalizerResult {
        val type = variables[ast.id.name]
        if (type == null) {
            return Failure("${ast.id.name} hasnt been declared")
        }
        val expressionType = ast.value!!.accept(TypeVisitor(variables))
        if (expressionType is Success && type == expressionType.type) {
            return Success(type)
        }
        if (expressionType is Success) {
            return Failure("Cannot assign a ${expressionType.type} to a $type")
        }
        return expressionType
    }
}
