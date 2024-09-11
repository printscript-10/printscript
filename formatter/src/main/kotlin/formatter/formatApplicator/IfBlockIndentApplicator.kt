package formatter.formatApplicator

import formatter.FormatApplicatorError
import formatter.FormatApplicatorResult
import formatter.FormatApplicatorSuccess
import formatter.FormatterConfig
import utils.AST
import utils.IfStatement
import utils.Token
import utils.TokenType

class IfBlockIndentApplicator(val formatters: List<FormatApplicator>, private val config: FormatterConfig) : FormatApplicator {
    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorResult {
        // Ensure the AST is an IfStatement, otherwise return success with original tokens
        if (ast !is IfStatement) return FormatApplicatorSuccess(tokens)

        var result = tokens.toMutableList()
        val errors = mutableListOf<FormatApplicatorError>()

        // Ensure the 'if' block is on the same line, meaning 'if', condition, and '{' should be together.
        val ifTokenIndex = tokens.indexOfFirst { it.type == TokenType.IF }
        if (ifTokenIndex != -1) {
            // Apply formatting to 'if (condition) {' to ensure it's on one line
            result = formatIfConditionOnSameLine(result, ifTokenIndex)
        }

        // Now, we need to apply formatting inside the code block, i.e., inside '{ }'.
        val openBraceIndex = tokens.indexOfFirst { it.type == TokenType.OPEN_BRACE }
        val closeBraceIndex = tokens.indexOfFirst { it.type == TokenType.CLOSE_BRACE }

        // Check if both braces exist in the token list
        if (openBraceIndex != -1 && closeBraceIndex != -1) {
            // Extract the tokens inside the block (between { and })
            val blockTokens = result.subList(openBraceIndex + 1, closeBraceIndex)

            // Apply indentation to the block's tokens based on the configuration
            val formattedBlockTokens = applyIndentation(blockTokens, config.if_block_indent_spaces)

            // Replace the tokens inside the block with the formatted version
            result = (result.subList(0, openBraceIndex + 1) +
                formattedBlockTokens +
                result.subList(closeBraceIndex, result.size)).toMutableList()
        }

        return if (errors.isEmpty()) {
            FormatApplicatorSuccess(result)
        } else {
            FormatApplicatorError("placeholderError")
        }
    }

    // Helper function to format 'if (condition) {' to be on the same line
    private fun formatIfConditionOnSameLine(tokens: MutableList<Token>, ifTokenIndex: Int): MutableList<Token> {
        // Make sure that the tokens between 'if' and '{' are on the same line
        for (i in ifTokenIndex until tokens.size) {
            val token = tokens[i]
            if (token.type == TokenType.OPEN_BRACE) {
                break
            }
            // Replace any newlines or add necessary spaces to ensure it's all on the same line
            if (token.type == TokenType.WHITESPACE && token.value == "\n") {
                tokens[i] = Token(TokenType.WHITESPACE, " ", token.position)
            }
        }
        return tokens
    }

    // Helper function to apply indentation based on if_block_indent_space configuration
    private fun applyIndentation(blockTokens: List<Token>, indentSpaces: Int): MutableList<Token> {
        val resultTokens = mutableListOf<Token>()
        var newLineStarted = true

        // Add indentation for each line within the block
        for (token in blockTokens) {
            if (newLineStarted) {
                // Add the required number of spaces at the beginning of each new line
                resultTokens.add(Token(TokenType.WHITESPACE, " ".repeat(indentSpaces), token.position))
                newLineStarted = false
            }

            // Add the current token to the result
            resultTokens.add(token)

            // Detect the start of a new line (i.e., after a semicolon or newline token)
            if (token.type == TokenType.SEMICOLON || (token.type == TokenType.WHITESPACE && token.value == "\n")) {
                newLineStarted = true
            }
        }

        return resultTokens
    }


}
