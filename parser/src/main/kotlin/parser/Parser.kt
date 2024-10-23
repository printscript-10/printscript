package parser

import ast.AST
import ast.VariableType
import parser.nodeBuilder.ASTBuilder
import parser.nodeBuilder.BuildFailure
import parser.nodeBuilder.BuildSuccess
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import result.Result
import token.Token

class Parser(
    private val version: String,
    variables: Map<String, VariableType>,
) {

    private val semanticAnalyzer = SemanticAnalyzer(variables)

    fun buildAST(tokens: List<Token>): Result {
        val astResult = ASTBuilder(version).build(tokens)
        return if (astResult is BuildFailure) {
            ParseFailure(astResult.error)
        } else {
            checkAST((astResult as BuildSuccess).result)
        }
    }

    private fun checkAST(ast: AST): Result {
        val semanticAnalyzerResult = semanticAnalyzer.analyze(ast)
        return if (semanticAnalyzerResult is Failure) {
            ParseFailure(semanticAnalyzerResult.error)
        } else {
            ParseSuccess(ast, (semanticAnalyzerResult as Success).variables)
        }
    }
}
