package interpreter

import result.Failure
import result.Success

data class InterpretSuccess(val result: Map<String, Variable>) : Success
data class InterpretFailure(override val error: String) : Failure

data class ExpressionSuccess(val value: Variable) : Success
data class ExpressionFailure(override val error: String) : Failure
