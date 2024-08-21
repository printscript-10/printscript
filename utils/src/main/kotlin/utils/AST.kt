package utils

import utils.Position

sealed interface AST {
    val position: Position

    fun <T> accept(visitor: ASTVisitor<T>): T
}

// :p
sealed interface ASTVisitor<T> {
    fun visitLiteral(literal: Literal): T
    fun visitVariableDeclaration(variableDeclaration: VariableDeclaration): T
    fun visitPrintFunction(printFunction: PrintFunction): T
    fun visitBinaryOperation(binaryOperation: BinaryOperation): T
    fun visitIdentifier(id: Identifier): T
}



data class PrintFunction(
    val value: AST,
    override val position: Position
) : AST{

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visitPrintFunction(this)
    }
}

data class BinaryOperation(
    val right: AST,
    val left: AST,
    val operator: String,
    override val position: Position
): AST

data class VariableDeclaration(
    val id: Identifier,
    val type: Type,
    val init: AST?,
    override val position: Position
): AST

data class Identifier(
    val name: String,
    override val position: Position,
): AST

data class Type(
    val name: String,
    override val position: Position
): AST

sealed interface Literal: AST {
    val value: Any
}

data class NumberLiteral(
    override val value: Number,
    override val position: Position,
): Literal

data class StringLiteral(
    override val value: String,
    override val position: Position
): Literal
