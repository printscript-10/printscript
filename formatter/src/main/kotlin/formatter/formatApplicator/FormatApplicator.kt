package formatter.formatApplicator

import formatter.FormatApplicatorResult
import utils.AST
import utils.Token

interface FormatApplicator {
    fun apply(tokens: List<Token>, ast: AST): FormatApplicatorResult
}
