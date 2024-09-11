package parser.nodeBuilder

import utils.AST
import utils.Expression
import utils.IfStatement
import utils.Result
import utils.Token
import utils.TokenType

class IfStatementBuilder : ASTNodeBuilder {
    override fun build(tokens: List<Token>, position: Int): Result {
        val openBracketIndex = position + 1
        val closingBracketIndex = position + 3
        val openingBraceIndex = position + 4

        if (
            tokens[position].type != TokenType.IF ||
            tokens[openBracketIndex].type != TokenType.OPEN_BRACKET ||
            tokens[closingBracketIndex].type != TokenType.CLOSE_BRACKET ||
            tokens[openingBraceIndex].type != TokenType.OPEN_BRACE
        ) {
            return BuildFailure(
                error = "Invalid if statement format",
                position = position,
            )
        }
        val expressionTokens = tokens.subList(openBracketIndex + 1, closingBracketIndex)
        val expressionResult = ExpressionBuilder().build(expressionTokens, position)
        if (expressionResult is BuildFailure) return expressionResult

        var currentIndex = openingBraceIndex + 1
        val thenStatements = mutableListOf<AST>()

        while (tokens[currentIndex].type != TokenType.CLOSE_BRACE) {
            val statementTokens = extractStatement(tokens, currentIndex)
            // TODO sacar el 1.1 ese
            val thenStatementResult = ASTBuilder("1.1").build(statementTokens)

            if (thenStatementResult is BuildFailure) {
                return BuildFailure(
                    error = "Invalid then statement: ${thenStatementResult.error}",
                    position = currentIndex,
                )
            }
            thenStatements.add((thenStatementResult as BuildSuccess).result)
            currentIndex += statementTokens.size
        }
        if (thenStatements.isEmpty()) {
            return BuildFailure(
                error = "If statement cannot have empty body",
                position = currentIndex,
            )
        }

        currentIndex++
        val elseStatements = mutableListOf<AST>()
        if (currentIndex < tokens.size && tokens[currentIndex].type == TokenType.ELSE) {
            currentIndex++
            if (tokens[currentIndex].type != TokenType.OPEN_BRACE) {
                return BuildFailure(
                    error = "Invalid else statement format",
                    position = currentIndex,
                )
            }
            currentIndex++

            while (tokens[currentIndex].type != TokenType.CLOSE_BRACE) {
                val statementTokens = extractStatement(tokens, currentIndex)
                val elseStatementResult = ASTBuilder("1.1").build(statementTokens)

                if (elseStatementResult is BuildFailure) {
                    return BuildFailure(
                        error = "Invalid else statement: ${elseStatementResult.error}",
                        position = currentIndex,
                    )
                }

                elseStatements.add((elseStatementResult as BuildSuccess).result)
                currentIndex += statementTokens.size
            }
        }

        return BuildSuccess(
            result = IfStatement(
                condition = (expressionResult as BuildSuccess).result as Expression,
                thenStatements = thenStatements,
                elseStatements = elseStatements,
                position = tokens[position].position,
            ),
            position = position,
        )
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
