package parser.semanticAnalizer

import ast.ASTExpressionVisitor
import ast.BinaryOperation
import ast.BinaryOperators
import ast.BooleanLiteral
import ast.Identifier
import ast.Literal
import ast.NumberLiteral
import ast.ReadEnv
import ast.ReadInput
import ast.StringLiteral
import ast.VariableType

class TypeVisitor(private val variables: Map<String, VariableType>) : ASTExpressionVisitor<SemanticAnalyzerResult> {
    override fun visitLiteral(literal: Literal): SemanticAnalyzerResult {
        return when (literal) {
            is NumberLiteral -> VisitSuccess(VariableType.NUMBER)
            is BooleanLiteral -> VisitSuccess(VariableType.BOOLEAN)
            is StringLiteral -> VisitSuccess(VariableType.STRING)
        }
    }

    override fun visitBinaryOperation(binaryOperation: BinaryOperation): SemanticAnalyzerResult {
        val left = binaryOperation.left.accept(this)
        val right = binaryOperation.right.accept(this)

        if (left !is VisitSuccess) return left
        if (right !is VisitSuccess) return right

        if (
            (left.type == VariableType.NUMBER || left.type == VariableType.UNKNOWN) &&
            (right.type == VariableType.NUMBER || right.type == VariableType.UNKNOWN)
        ) {
            return if (binaryOperation.operator == BinaryOperators.PLUS) {
                VisitSuccess(VariableType.UNKNOWN)
            } else {
                VisitSuccess(VariableType.NUMBER)
            }
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

    override fun visitReadEnv(readEnv: ReadEnv): SemanticAnalyzerResult {
        return VisitSuccess(VariableType.UNKNOWN)
    }

    override fun visitReadInput(readInput: ReadInput): SemanticAnalyzerResult {
        return VisitSuccess(VariableType.UNKNOWN)
    }
}
