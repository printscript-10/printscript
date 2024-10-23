package linter

import result.Failure
import result.Success

data object LinterSuccess : Success
data class LinterFailure(override val error: String) : Failure

data class LintingError(val message: String)
