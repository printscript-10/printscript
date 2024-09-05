package parser

import utils.AST
import utils.Failure
import utils.Success
import utils.VariableType

data class ParseSuccess(val result: AST, val variables: Map<String, VariableType>) : Success
data class ParseFailure(override val error: String) : Failure
