package parser.semanticAnalizer

sealed interface SemanticAnalizerResult

data class Success(val type: String) : SemanticAnalizerResult
data class Failure(val error: String) : SemanticAnalizerResult
