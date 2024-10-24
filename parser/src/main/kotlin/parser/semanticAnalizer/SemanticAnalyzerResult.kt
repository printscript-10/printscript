package parser.semanticAnalizer

import ast.VariableType

sealed interface SemanticAnalyzerResult

data class Success(val variables: Map<String, VariableType>) : SemanticAnalyzerResult
data class VisitSuccess(val type: VariableType) : SemanticAnalyzerResult
data class Failure(val error: String) : SemanticAnalyzerResult
