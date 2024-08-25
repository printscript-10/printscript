package parser

import parser.nodeBuilder.ASTBuilder
import parser.nodeBuilder.BuildFailure
import parser.nodeBuilder.BuildSuccess
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.ParseFailure
import parser.semanticAnalizer.ParseSuccess
import parser.semanticAnalizer.ParsingResult
import parser.semanticAnalizer.SemanticAnalizer
import utils.AST
import utils.Token

class Parser {

    // un asco todos los results pero es lo que hay
    private val semanticAnalizer = SemanticAnalizer()

    fun buildAST(tokens: List<Token>): ParsingResult {
        val astResult = ASTBuilder().build(tokens)
        if (astResult is BuildSuccess) {
            return checkAST(astResult.result)
        }
        return ParseFailure((astResult as BuildFailure).error)
    }

    fun checkAST(ast: AST): ParsingResult {
        val semanticAnalizerResult = semanticAnalizer.analize(ast)
        if (semanticAnalizerResult is Failure) {
            return ParseFailure(semanticAnalizerResult.error)
        }
        return ParseSuccess(ast)
    }
}
