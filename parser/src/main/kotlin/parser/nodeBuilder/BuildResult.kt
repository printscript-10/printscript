package parser.nodeBuilder

import ast.AST
import result.Failure
import result.Success

data class BuildSuccess(val result: AST, val position: Int) : Success
data class BuildFailure(override val error: String) : Failure
