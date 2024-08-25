package parser.semanticAnalizer

import utils.AST

sealed interface ParsingResult

data class ParseSuccess(val result: AST) : ParsingResult
data class ParseFailure(val error: String) : ParsingResult
