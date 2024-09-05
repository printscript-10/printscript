package interpreter

import utils.Failure
import utils.Success

data class InterpretSuccess(val result: Map<String, Variable>) : Success
data class InterpretFailure(override val error: String) : Failure
