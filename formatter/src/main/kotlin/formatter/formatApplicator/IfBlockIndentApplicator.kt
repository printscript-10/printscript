package formatter.formatApplicator

import formatter.FormatApplicatorError
import formatter.FormatApplicatorResult
import formatter.FormatApplicatorSuccess
import formatter.Formatter
import formatter.FormatterConfig
import utils.AST
import utils.IfStatement
import utils.Token
import utils.TokenType

class IfBlockIndentApplicator(private val config: FormatterConfig, private val version: String) : FormatApplicator {

    override fun apply(tokens: List<Token>, ast: AST): FormatApplicatorResult {
        if (ast !is IfStatement) return FormatApplicatorSuccess(tokens)

        val indentSpaces = config.if_block_indent_spaces * config.base_indent_level
        val result: MutableList<Token> = mutableListOf()
        val errors = mutableListOf<FormatApplicatorError>()

        val openBraceIndex = tokens.indexOfFirst { it.type == TokenType.OPEN_BRACE }

        if (openBraceIndex != -1) {
            result.addAll(tokens.subList(0, openBraceIndex + 1))
            result.add(Token(TokenType.WHITESPACE, "\n", tokens[openBraceIndex].position))
        }

        var currentIndex = openBraceIndex + 1
        var astIndex = 0
        // TODO cambiar el update del config
        val updatedConfig = config.copy(base_indent_level = config.base_indent_level + 1)
        while (tokens[currentIndex].type != TokenType.CLOSE_BRACE && currentIndex < tokens.size) {
            val statementTokens = extractStatement(tokens, currentIndex)
            val thenStatementResult = Formatter(updatedConfig, version).format(
                statementTokens,
                ast.thenStatements[astIndex],
            )

            if (thenStatementResult is FormatApplicatorError) {
                errors.add(thenStatementResult)
            } else {
                result.add(Token(TokenType.WHITESPACE, " ".repeat(indentSpaces), tokens[currentIndex].position))
                result.addAll((thenStatementResult as FormatApplicatorSuccess).tokens)
                result.add(Token(TokenType.WHITESPACE, "\n", tokens[currentIndex].position))
            }
            currentIndex += statementTokens.size
            astIndex++
        }
        result.add(
            Token(
                TokenType.CLOSE_BRACE,
                " ".repeat(indentSpaces - config.if_block_indent_spaces) + "}",
                tokens[currentIndex - 1].position,
            ),
        )
        currentIndex++
        astIndex = 0

        if (currentIndex < tokens.size && tokens[currentIndex].type == TokenType.ELSE) {
            result.addAll(tokens.subList(currentIndex, currentIndex + 2))
            result.add(Token(TokenType.WHITESPACE, "\n", tokens[openBraceIndex].position))
            currentIndex += 2
            while (tokens[currentIndex].type != TokenType.CLOSE_BRACE) {
                val statementTokens = extractStatement(tokens, currentIndex)
                val elseStatementResult = Formatter(updatedConfig, version).format(
                    statementTokens,
                    ast.elseStatements[astIndex],
                )

                if (elseStatementResult is FormatApplicatorError) {
                    errors.add(elseStatementResult)
                } else {
                    result.add(Token(TokenType.WHITESPACE, " ".repeat(indentSpaces), tokens[currentIndex].position))
                    result.addAll((elseStatementResult as FormatApplicatorSuccess).tokens)
                    result.add(Token(TokenType.WHITESPACE, "\n", tokens[currentIndex].position))
                }
                currentIndex += statementTokens.size
                astIndex++
            }
            result.add(
                Token(
                    TokenType.CLOSE_BRACE,
                    " ".repeat(indentSpaces - config.if_block_indent_spaces) + "}",
                    tokens[currentIndex - 1].position,
                ),
            )
        }
        return if (errors.isEmpty()) {
            FormatApplicatorSuccess(result)
        } else {
            FormatApplicatorError(errors.joinToString("\n"))
        }
    }

    private fun extractStatement(tokens: List<Token>, startIndex: Int): List<Token> {
        val statementTokens = mutableListOf<Token>()
        var index = startIndex

        while (
            index < tokens.size && tokens[index].type != TokenType.SEMICOLON &&
            tokens[index].type != TokenType.CLOSE_BRACE
        ) {
            if (tokens[index].type == TokenType.IF) {
                statementTokens.add(tokens[index])
                index++

                while (index < tokens.size && tokens[index].type != TokenType.CLOSE_BRACKET) {
                    statementTokens.add(tokens[index])
                    index++
                }
                if (index < tokens.size && tokens[index].type == TokenType.CLOSE_BRACKET) {
                    statementTokens.add(tokens[index])
                    index++
                }

                if (index < tokens.size && tokens[index].type == TokenType.OPEN_BRACE) {
                    var braceCount = 1
                    statementTokens.add(tokens[index])
                    index++

                    while (index < tokens.size && braceCount > 0) {
                        if (tokens[index].type == TokenType.OPEN_BRACE) {
                            braceCount++
                        } else if (tokens[index].type == TokenType.CLOSE_BRACE) {
                            braceCount--
                        }
                        statementTokens.add(tokens[index])
                        index++
                    }
                }

                if (index < tokens.size && tokens[index].type == TokenType.ELSE) {
                    statementTokens.add(tokens[index])
                    index++

                    if (index < tokens.size && tokens[index].type == TokenType.OPEN_BRACE) {
                        var braceCount = 1
                        statementTokens.add(tokens[index])
                        index++

                        while (index < tokens.size && braceCount > 0) {
                            if (tokens[index].type == TokenType.OPEN_BRACE) {
                                braceCount++
                            } else if (tokens[index].type == TokenType.CLOSE_BRACE) {
                                braceCount--
                            }
                            statementTokens.add(tokens[index])
                            index++
                        }
                    }
                }
            } else {
                statementTokens.add(tokens[index])
                index++
            }
        }

        if (index < tokens.size && tokens[index].type == TokenType.SEMICOLON) {
            statementTokens.add(tokens[index])
        }
        return statementTokens
    }
}
