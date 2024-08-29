package parser.semanticAnalizer

import utils.ASTExpressionVisitor
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Identifier
import utils.Literal
import utils.NumberLiteral
import utils.VariableType

class TypeVisitor(private val variables: Map<String, VariableType>) : ASTExpressionVisitor<SemanticAnalyzerResult> {
    override fun visitLiteral(literal: Literal): SemanticAnalyzerResult {
        if (literal is NumberLiteral) return VisitSuccess(VariableType.NUMBER)

        return VisitSuccess(VariableType.STRING)
    }

    override fun visitBinaryOperation(binaryOperation: BinaryOperation): SemanticAnalyzerResult {
        val left = binaryOperation.left.accept(this)
        val right = binaryOperation.right.accept(this)

        if (left !is VisitSuccess) return left
        if (right !is VisitSuccess) return right

        if (left.type == VariableType.NUMBER && right.type == VariableType.NUMBER) {
            return VisitSuccess(VariableType.NUMBER)
        }

        if (binaryOperation.operator != BinaryOperators.PLUS) {
            return Failure("${binaryOperation.operator} can only be used with numbers")
        }

        return VisitSuccess(VariableType.STRING)
    }

    override fun visitIdentifier(id: Identifier): SemanticAnalyzerResult {
        val variableType = variables[id.name] ?: return Failure("${id.name} hasn't been declared")
        return VisitSuccess(variableType)
    }
}
