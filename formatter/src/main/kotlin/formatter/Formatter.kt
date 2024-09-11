package formatter

import formatter.formatApplicator.AssignationEqualWrapWhitespacesApplicator
import formatter.formatApplicator.BinaryOperatorWrapWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonLeadingWhitespacesApplicator
import formatter.formatApplicator.DeclarationColonTrailingWhitespacesApplicator
import formatter.formatApplicator.FormatApplicator
import formatter.formatApplicator.IfBlockIndentApplicator
import formatter.formatApplicator.MandatoryWhitespaceApplicator
import formatter.formatApplicator.PrintTrailingLineJumpApplicator
import utils.AST
import utils.Result
import utils.Token
import utils.TokenType

class Formatter(private val config: FormatterConfig) {

    private val formatters: List<FormatApplicator> by lazy {
        listOf(
            AssignationEqualWrapWhitespacesApplicator(config),
            DeclarationColonLeadingWhitespacesApplicator(config),
            DeclarationColonTrailingWhitespacesApplicator(config),
            PrintTrailingLineJumpApplicator(config),
            MandatoryWhitespaceApplicator(),
            BinaryOperatorWrapWhitespacesApplicator(config)
        )
    }

    fun format(tokens: List<Token>, ast: AST): Result {
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
        if (errors.isNotEmpty()) return FormatFailure(errors.joinToString("\n") { it.message })
        return FormatSuccess(concatenateTokenValues(result))
    }

    private fun concatenateTokenValues(tokens: List<Token>): String {
        return tokens.joinToString("") { token ->
            if (token.type == TokenType.STRING) {
                "\"${token.value}\""
            } else {
                token.value
            }
        } + "\n"
    }
}
