package interpreter.nodeInterpreter

import interpreter.Variable
import utils.AST
import utils.Declaration
import utils.Expression
import utils.Result

sealed interface ASTNodeInterpreter<T, A : AST> {
    fun execute(ast: A): T
}

sealed interface ASTDeclarationInterpreter<B : Declaration> : ASTNodeInterpreter<Result, B> {
    override fun execute(ast: B): Result
}

sealed interface ASTExpressionInterpreter<B : Expression> : ASTNodeInterpreter<Variable, B> {
    override fun execute(ast: B): Variable
}
