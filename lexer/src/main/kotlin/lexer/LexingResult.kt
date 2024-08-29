package lexer

import utils.Token

sealed interface LexingResult

data class LexingSuccess(val tokens: List<Token>) : LexingResult
data class LexingFailure(val error: String) : LexingResult
