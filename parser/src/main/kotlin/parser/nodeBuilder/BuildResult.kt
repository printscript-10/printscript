package parser.nodeBuilder

import utils.AST

sealed interface BuildResult
data class BuildSuccess(val result: AST, val position: Int) : BuildResult
data class BuildFailure(val error: String, val position: Int) : BuildResult
