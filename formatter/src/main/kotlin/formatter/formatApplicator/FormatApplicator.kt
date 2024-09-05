package formatter.formatApplicator

import formatter.FormatterConfig
import utils.AST
import utils.Token

interface FormatApplicator {
    fun apply(tokens: List<Token>, ast: AST, config: FormatterConfig): List<Token>
}
