package utils

sealed interface AST {
    val position: Position
}

sealed interface Expression: AST{
    fun <T> accept(visitor: ASTExpressionVisitor<T>): T
}

interface ASTExpressionVisitor<T>{
    fun visitLiteral(literal: Literal): T
    fun visitBinaryOperation(binaryOperation: BinaryOperation): T
    fun visitIdentifier(id: Identifier): T
}
data class PrintFunction(
    val value: Expression,
    override val position: Position
) : AST

data class VariableDeclaration(
    val id: Identifier,
    val type: Type,
    val init: Expression?,
    override val position: Position
): AST

data class VariableAssignation(
    val id: Identifier,
    val value: AST,
    override val position: Position
): AST

data class Type(
    val name: String,
    override val position: Position
): AST

data class BinaryOperation(
    val right: Expression,
    val left: Expression,
    val operator: String,
    override val position: Position
): Expression {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T {
        return visitor.visitBinaryOperation(this)
    }
}

data class Identifier(
    val name: String,
    override val position: Position,
): Expression  {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T {
        return visitor.visitIdentifier(this)
    }
}

sealed interface Literal: Expression {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T{
        return visitor.visitLiteral(this)
    }
}

data class NumberLiteral(
    val value: Number,
    override val position: Position,
): Literal

data class StringLiteral(
    val value: String,
    override val position: Position
): Literal
