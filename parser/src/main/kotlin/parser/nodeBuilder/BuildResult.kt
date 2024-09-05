package parser.nodeBuilder

import utils.AST
import utils.Failure
import utils.Success

data class BuildSuccess(val result: AST, val position: Int) : Success
data class BuildFailure(override val error: String, val position: Int) : Failure
