package interpreter.nodeInterpreter

import interpreter.BooleanVariable
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import utils.BooleanLiteral
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral

class StringLiteralInterpreter : ASTExpressionInterpreter<StringLiteral> {
    override fun execute(ast: StringLiteral): Variable {
        return StringVariable(ast.value, false)
    }
}

class NumericLiteralInterpreter : ASTExpressionInterpreter<NumberLiteral> {
    override fun execute(ast: NumberLiteral): Variable {
        return NumericVariable(ast.value, false)
    }
}

class BooleanLiteralInterpreter : ASTExpressionInterpreter<BooleanLiteral> {
    override fun execute(ast: BooleanLiteral): Variable {
        return BooleanVariable(ast.value, false)
    }
}

class IdentifierInterpreter(private val variables: Map<String, Variable>) : ASTExpressionInterpreter<Identifier> {
    override fun execute(ast: Identifier): Variable {
        return variables[ast.name]!!
    }
}
