package parser.semanticAnalizer

import utils.AST
import utils.VariableType

sealed interface ParsingResult

data class ParseSuccess(val result: AST, val variables: Map<String, VariableType>) : ParsingResult
data class ParseFailure(val error: String) : ParsingResult
