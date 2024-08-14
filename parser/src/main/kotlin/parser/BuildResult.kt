package parser

import utils.AST

sealed interface BuildResult
data class Success(val result: AST, val position: Int) : BuildResult
data class Failure(val error: String, val position: Int) : BuildResult
