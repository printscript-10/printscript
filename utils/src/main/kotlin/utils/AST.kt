package utils

sealed interface AST {
    val position: Position
}

sealed interface Expression : AST {
    fun <T> accept(visitor: ASTExpressionVisitor<T>): T
}

sealed interface Declaration : AST

interface ASTExpressionVisitor<T> {
    fun visitLiteral(literal: Literal): T
    fun visitBinaryOperation(binaryOperation: BinaryOperation): T
    fun visitIdentifier(id: Identifier): T
}
data class PrintFunction(
    val value: Expression,
    override val position: Position,
) : Declaration

data class VariableDeclaration(
    val id: Identifier,
    val type: Type,
    val init: Expression?,
    val isFinal: Boolean,
    override val position: Position,
) : Declaration

data class VariableAssignation(
    val id: Identifier,
    val value: Expression,
    override val position: Position,
) : Declaration

// TODO : cambiar thenStatements a list de Declaration
data class IfStatement(
    val condition: Expression,
    val thenStatements: List<AST>,
    val elseStatements: List<AST>,
    override val position: Position,
) : Declaration

data class Type(
    val name: VariableType,
    override val position: Position,
) : AST

data class BinaryOperation(
    val right: Expression,
    val left: Expression,
    val operator: BinaryOperators,
    override val position: Position,
) : Expression {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T {
        return visitor.visitBinaryOperation(this)
    }
}

data class Identifier(
    val name: String,
    override val position: Position,
) : Expression {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T {
        return visitor.visitIdentifier(this)
    }
}

sealed interface Literal : Expression {
    override fun <T> accept(visitor: ASTExpressionVisitor<T>): T {
        return visitor.visitLiteral(this)
    }
}

data class NumberLiteral(
    val value: Number,
    override val position: Position,
) : Literal

data class StringLiteral(
    val value: String,
    override val position: Position,
) : Literal

data class BooleanLiteral(
    val value: Boolean,
    override val position: Position,
) : Literal
