package formatter

import formatter.formatApplicator.AssignationEqualWrapWhistepaces
import formatter.formatApplicator.BinaryOperatorWrapWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonLeadingWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonTrailingWhitespacesApplicator
import formatter.formatApplicator.MandatoryWhitespaceApplicator
import formatter.formatApplicator.PrintTrailingLinejumpApplicator
import utils.AST
import utils.Token
import utils.TokenType

data class FormatterConfig(
    val declaration_colon_trailing_whitespaces: Boolean = false,
    val declaration_colon_leading_whitespaces: Boolean,
    val assignation_equal_wrap_whitespaces: Boolean,
    val println_trailing_linejump: Int,
)

class Formatter(private val config: FormatterConfig) {

    private val formatters = listOf(
        AssignationEqualWrapWhistepaces(),
        DeclarationColonLeadingWhitespacesApplicator(),
        DeclarationColonTrailingWhitespacesApplicator(),
        PrintTrailingLinejumpApplicator(),
        MandatoryWhitespaceApplicator(),
        BinaryOperatorWrapWhitespacesApplicator(),
    )

    fun format(tokens: List<Token>, ast: AST): String {
        var result = tokens
        for (formatter in formatters) { result = formatter.apply(result, ast, config) }
        return concatenateTokenValues(result)
    }

    fun concatenateTokenValues(tokens: List<Token>): String {
        return tokens.joinToString("") { token ->
            if (token.type == TokenType.STRING) {
                "\"${token.value}\""
            } else {
                token.value
            }
        } + "\n"
    }
}
