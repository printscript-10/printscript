package lexer

import result.Failure
import result.Success
import token.Token

data class LexingSuccess(val tokens: List<Token>) : Success
data class LexingFailure(override val error: String) : Failure
