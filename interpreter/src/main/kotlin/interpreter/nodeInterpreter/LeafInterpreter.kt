package interpreter.nodeInterpreter

import ast.BooleanLiteral
import ast.Identifier
import ast.NumberLiteral
import ast.StringLiteral
import interpreter.BooleanVariable
import interpreter.ExpressionSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable

class StringLiteralInterpreter : ASTExpressionInterpreter<StringLiteral> {
    override fun execute(ast: StringLiteral): ExpressionSuccess {
        return ExpressionSuccess(StringVariable(ast.value, false))
    }
}

class NumericLiteralInterpreter : ASTExpressionInterpreter<NumberLiteral> {
    override fun execute(ast: NumberLiteral): ExpressionSuccess {
        return ExpressionSuccess(NumericVariable(ast.value, false))
    }
}

class BooleanLiteralInterpreter : ASTExpressionInterpreter<BooleanLiteral> {
    override fun execute(ast: BooleanLiteral): ExpressionSuccess {
        return ExpressionSuccess(BooleanVariable(ast.value, false))
    }
}

class IdentifierInterpreter(private val variables: Map<String, Variable>) : ASTExpressionInterpreter<Identifier> {
    override fun execute(ast: Identifier): ExpressionSuccess {
        return ExpressionSuccess(variables[ast.name]!!)
    }
}
