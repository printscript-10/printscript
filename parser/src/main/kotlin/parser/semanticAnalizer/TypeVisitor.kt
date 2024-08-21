package parser.semanticAnalizer

import utils.ASTVisitor
import utils.BinaryOperation
import utils.Identifier
import utils.Literal
import utils.PrintFunction
import utils.StringLiteral
import utils.Type
import utils.VariableDeclaration

class TypeVisitor: ASTVisitor<String> {

    private val context: Map<String, String> = mapOf()

    override fun visitLiteral(literal: Literal): String {
        if(literal is StringLiteral){
            return "string"
        }
        return "number"
    }

    override fun visitVariableDeclaration(variableDeclaration: VariableDeclaration): String {
        return variableDeclaration.type.name
    }

    override fun visitPrintFunction(printFunction: PrintFunction): String {
        return printFunction.value.accept(this)
    }

    override fun visitBinaryOperation(binaryOperation: BinaryOperation): String {

    }

    override fun visitIdentifier(id: Identifier): String {
        TODO("Not yet implemented")
    }

    override fun visitType(type: Type): String {
        TODO("Not yet implemented")
    }
}
