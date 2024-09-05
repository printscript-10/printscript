package interpreter

sealed interface InterpretResult

data class InterpretSuccess(val result: Map<String, Variable>) : InterpretResult
data class InterpretFailure(val error: String) : InterpretResult
