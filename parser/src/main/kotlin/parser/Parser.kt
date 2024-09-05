package parser

import parser.nodeBuilder.ASTBuilder
import parser.nodeBuilder.BuildFailure
import parser.nodeBuilder.BuildSuccess
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.ParseFailure
import parser.semanticAnalizer.ParseSuccess
import parser.semanticAnalizer.ParsingResult
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.AST
import utils.Token
import utils.VariableType

class Parser(variables: Map<String, VariableType>) {

    private val semanticAnalyzer = SemanticAnalyzer(variables)

    fun buildAST(tokens: List<Token>): ParsingResult {
        return when (val astResult = ASTBuilder().build(tokens)) {
            is BuildFailure -> ParseFailure(astResult.error)
            is BuildSuccess -> checkAST(astResult.result)
        }
    }

    private fun checkAST(ast: AST): ParsingResult {
        val semanticAnalyzerResult = semanticAnalyzer.analyze(ast)
        return if (semanticAnalyzerResult is Failure) {
            ParseFailure(semanticAnalyzerResult.error)
        } else {
            ParseSuccess(ast, (semanticAnalyzerResult as Success).variables)
        }
    }
}
