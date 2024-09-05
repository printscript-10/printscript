package lexer

import utils.Failure
import utils.Success
import utils.Token

data class LexingSuccess(val tokens: List<Token>) : Success
data class LexingFailure(override val error: String) : Failure
