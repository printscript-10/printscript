package parser.semanticAnalizer

import utils.AST
import utils.PrintFunction
import utils.VariableDeclaration

class SemanticAnalizer {

    var variables: Map<String, String> = mapOf()

    fun analize(ast: AST): Boolean {
        when (ast) {
            is VariableDeclaration -> return checkVariableDeclaration(ast)
            is PrintFunction -> checkPrintFunction(ast)
            else -> return false
        }
    }

    private fun checkVariableDeclaration(ast: VariableDeclaration): Boolean {
        val type = ast.type
        if (ast.init == null) {
            return true
        }
        val expressionType = ast.init!!.accept(TypeVisitor(variables))
        return expressionType == ast.type.name
    }
}
