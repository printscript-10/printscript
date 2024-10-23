package formatter

import ast.AST
import formatter.formatApplicator.AssignationEqualWrapWhitespacesApplicator
import formatter.formatApplicator.BinaryOperatorWrapWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonLeadingWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonTrailingWhitespacesApplicator
import formatter.formatApplicator.FormatApplicator
import formatter.formatApplicator.IfBlockIndentApplicator
import formatter.formatApplicator.MandatoryWhitespaceApplicator
import formatter.formatApplicator.PrintTrailingLineJumpApplicator
import token.Token
import token.TokenType

class Formatter(config: FormatterConfig, version: String) {

    private val formatters: List<FormatApplicator>

    init {
        formatters = getFormatters(config, version)
    }

    fun format(tokens: List<Token>, ast: AST): FormatApplicatorResult {
        var result = tokens
        val errors: MutableList<FormatApplicatorError> = mutableListOf()
        for (formatter in formatters) {
            val formatResult = formatter.apply(result, ast)
            if (formatResult is FormatApplicatorSuccess) {
                result = formatResult.tokens
            } else if (formatResult is FormatApplicatorError) {
                errors.add(formatResult)
            }
        }
        if (errors.isNotEmpty()) return FormatApplicatorError(errors.joinToString("\n") { it.message })
        return FormatApplicatorSuccess(result)
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

    private fun getFormatters(config: FormatterConfig, version: String): List<FormatApplicator> {
        val baseFormatters: List<FormatApplicator> = listOfNotNull(
            config.assignation_equal_wrap_whitespaces?.let {
                AssignationEqualWrapWhitespacesApplicator(config)
            },

            config.declaration_colon_leading_whitespaces?.let {
                DeclarationColonLeadingWhitespacesApplicator(config)
            },
            config.declaration_colon_trailing_whitespaces?.let {
                DeclarationColonTrailingWhitespacesApplicator(config)
            },
            config.println_trailing_line_jump?.let {
                PrintTrailingLineJumpApplicator(config)
            },
            MandatoryWhitespaceApplicator(version),
            BinaryOperatorWrapWhitespacesApplicator(config),
        )
        return when (version) {
            "1.0" -> baseFormatters
            "1.1" -> listOf<FormatApplicator>(
                IfBlockIndentApplicator(config, version),
            ).toMutableList().apply { addAll(baseFormatters) }
            else -> throw IllegalArgumentException("Invalid version $version")
        }
    }
}
