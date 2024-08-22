package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral

class StringLiteralInterpreter : ASTNodeInterpreter<Variable> {
    override fun execute(ast: AST): Variable {
        if (ast !is StringLiteral) {
            throw Exception("AST isn't a StringLiteral")
        }
        return Variable(type = "string", value = ast.value)
    }
}

class NumericLiteralInterpreter : ASTNodeInterpreter<Variable> {
    override fun execute(ast: AST): Variable {
        if (ast !is NumberLiteral) {
            throw Exception("AST isn't a NumberLiteral")
        }
        return Variable(type = "number", value = ast.value.toString())
    }
}

class IdentifierInterpreter(private val variables: Map<String, Variable>) : ASTNodeInterpreter<Variable> {
    override fun execute(ast: AST): Variable {
        if (ast !is Identifier) {
            throw Exception("AST isn't an identifier")
        }
        val result = variables[ast.name]
        if (result == null) {
            throw Exception("AST isn't a identifier ${ast.name}")
        }
        if (result.value == null) {
            throw Exception("Variable ${ast.name} hasnt been initialized")
        }
        return Variable(result.type, result.value)
    }
}
