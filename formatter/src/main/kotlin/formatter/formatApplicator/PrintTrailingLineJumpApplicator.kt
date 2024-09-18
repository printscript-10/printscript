package formatter.formatApplicator

import formatter.FormatApplicatorError
import formatter.FormatApplicatorResult
import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import utils.AST
import utils.Position
import utils.PrintFunction
import utils.Token
import utils.TokenType

class PrintTrailingLineJumpApplicator(private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorResult {
        if (config.println_trailing_line_jump == null) return FormatApplicatorSuccess(tokens)
        if (ast !is PrintFunction) return FormatApplicatorSuccess(tokens)
        val lineJumpAmount = config.println_trailing_line_jump
        if (lineJumpAmount < 0 || lineJumpAmount > 2) return FormatApplicatorError("Invalid line jump amount")
        val resultTokens = tokens.toMutableList()
        resultTokens.add(
            0,
            Token(
                TokenType.WHITESPACE,
                ("\n").repeat(lineJumpAmount) + (" ").repeat(
                    config.if_block_indent_spaces * (config.base_indent_level - 1),
                ),
                Position(tokens.first().position.line, 0, 0),
            ),
        )
        return FormatApplicatorSuccess(resultTokens)
    }
}
