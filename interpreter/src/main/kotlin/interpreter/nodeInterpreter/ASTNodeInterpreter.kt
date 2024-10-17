package interpreter.nodeInterpreter

import ast.AST
import ast.Declaration
import ast.Expression
import result.Result

sealed interface ASTNodeInterpreter<T, A : AST> {
    fun execute(ast: A): T
}

sealed interface ASTDeclarationInterpreter<B : Declaration> : ASTNodeInterpreter<Result, B> {
    override fun execute(ast: B): Result
}

sealed interface ASTExpressionInterpreter<B : Expression> : ASTNodeInterpreter<Result, B> {
    override fun execute(ast: B): Result
}
