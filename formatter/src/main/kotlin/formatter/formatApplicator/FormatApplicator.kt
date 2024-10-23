package formatter.formatApplicator

import ast.AST
import formatter.FormatApplicatorResult
import token.Token

interface FormatApplicator {
    fun apply(tokens: List<Token>, ast: AST): FormatApplicatorResult
}
