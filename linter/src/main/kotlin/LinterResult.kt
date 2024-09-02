package lexer

import utils.Position

sealed interface LinterResult

class LinterSuccess : LinterResult
data class LinterFailure(val errors: List<LintingError>) : LinterResult

data class LintingError(val message: String, var position: Position)
