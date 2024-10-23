package parser

import ast.AST
import ast.VariableType
import result.Failure
import result.Success

data class ParseSuccess(val result: AST, val variables: Map<String, VariableType>) : Success
data class ParseFailure(override val error: String) : Failure
