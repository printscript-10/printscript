package lexer.linter

import utils.Failure
import utils.Success

data object LinterSuccess : Success
data class LinterFailure(override val error: String) : Failure

data class LintingError(val message: String)
