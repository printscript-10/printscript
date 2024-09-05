package interpreter.nodeInterpreter

import interpreter.InterpretResult
import interpreter.Variable
import utils.AST
import utils.Declaration
import utils.Expression

sealed interface ASTNodeInterpreter<T, A : AST> {
    fun execute(ast: A): T
}

sealed interface ASTDeclarationInterpreter<B : Declaration> : ASTNodeInterpreter<InterpretResult, B> {
    override fun execute(ast: B): InterpretResult
}

sealed interface ASTExpressionInterpreter<B : Expression> : ASTNodeInterpreter<Variable, B> {
    override fun execute(ast: B): Variable
}
