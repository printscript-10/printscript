package parser.semanticAnalizer

sealed interface SemanticAnalizerResult

// null object pattern ????????
data class Success(val type: String) : SemanticAnalizerResult
data class Failure(val error: String) : SemanticAnalizerResult
