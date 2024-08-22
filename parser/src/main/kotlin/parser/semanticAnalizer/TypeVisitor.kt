package parser.semanticAnalizer

import utils.ASTExpressionVisitor
import utils.BinaryOperation
import utils.Identifier
import utils.Literal
import utils.NumberLiteral

class TypeVisitor(private val variables: Map<String, String>) : ASTExpressionVisitor<SemanticAnalizerResult> {
    override fun visitLiteral(literal: Literal): SemanticAnalizerResult {
        if (literal is NumberLiteral) {
            return Success("number")
        }
        return Success("string")
    }

    override fun visitBinaryOperation(binaryOperation: BinaryOperation): SemanticAnalizerResult {
        val left = binaryOperation.left.accept(this)
        val right = binaryOperation.right.accept(this)

        if (!(left is Success)) return left
        if (!(right is Success)) return right

        if (left.type == "number" && right.type == "number") {
            return Success("number")
        }
        if (binaryOperation.operator != "+") {
            return Failure("${binaryOperation.operator} can only be used with numbers")
        }
        return Success("string")
    }

    override fun visitIdentifier(id: Identifier): SemanticAnalizerResult {
        val variableType = variables[id.name]
        if (variableType == null) {
            return Failure("${id.name} hasn't been declared")
        }
        return Success(variableType)
    }
}
