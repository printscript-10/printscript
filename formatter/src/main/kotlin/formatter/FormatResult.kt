package formatter

import utils.Failure
import utils.Success
import utils.Token

data class FormatSuccess(val result: String) : Success
data class FormatFailure(override val error: String) : Failure

sealed interface FormatApplicatorResult
data class FormatApplicatorSuccess(val tokens: List<Token>) : FormatApplicatorResult
data class FormatApplicatorError(val message: String) : FormatApplicatorResult
