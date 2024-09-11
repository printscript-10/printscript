package parser.nodeBuilder

import utils.BinaryOperation
import utils.BinaryOperators
import utils.Expression
import utils.Result
import utils.Token
import utils.TokenType

class ExpressionBuilder : ASTNodeBuilder {

    private val precedence = mapOf(
        BinaryOperators.PLUS to 1,
        BinaryOperators.MINUS to 1,
        BinaryOperators.TIMES to 2,
        BinaryOperators.DIV to 2,
    )

    override fun build(tokens: List<Token>, position: Int): Result {
        val output = mutableListOf<Expression>()
        val operators = mutableListOf<Token>()

        for ((tokenIndex, token) in tokens.withIndex()) {
            when (token.type) {
                TokenType.NUMBER -> {
                    output.add(NumericLiteralBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.IDENTIFIER -> {
                    output.add(IdentifierBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.BOOLEAN -> {
                    output.add(BooleanLiteralBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.STRING -> {
                    output.add(StringLiteralBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.BINARY_OPERATOR -> {
                    while (operators.isNotEmpty() && shouldPopOperator(operators.last(), token)) {
                        popOperatorToOutput(operators, output)
                    }
                    operators.add(token)
                }
                TokenType.OPEN_BRACKET -> {
                    operators.add(token)
                }
                TokenType.CLOSE_BRACKET -> {
                    while (operators.isNotEmpty() && operators.last().type != TokenType.OPEN_BRACKET) {
                        popOperatorToOutput(operators, output)
                    }
                    if (operators.isNotEmpty() && operators.last().type == TokenType.OPEN_BRACKET) {
                        operators.removeAt(operators.lastIndex)
                    } else {
                        return BuildFailure("Mismatched parentheses", position)
                    }
                }

                else -> {
                    return BuildFailure("Invalid token type: ${token.type} in expression", position)
                }
            }
        }

        while (operators.isNotEmpty()) {
            if (operators.last().type == TokenType.OPEN_BRACKET) {
                return BuildFailure("Mismatched parentheses", position)
            }
            popOperatorToOutput(operators, output)
        }

        return BuildSuccess(output.first(), position)
    }

    private fun shouldPopOperator(op1: Token, op2: Token): Boolean {
        if (op1.type == TokenType.OPEN_BRACKET || op2.type == TokenType.OPEN_BRACKET) return false
        val precedence1 = precedence[BinaryOperators.fromSymbol(op1.value)] ?: throw IllegalArgumentException(
            "Unknown operator: ${op1.value}",
        )
        val precedence2 = precedence[BinaryOperators.fromSymbol(op2.value)] ?: throw IllegalArgumentException(
            "Unknown operator: ${op2.value}",
        )
        return precedence1 > precedence2 || (precedence1 == precedence2)
    }

    private fun popOperatorToOutput(operators: MutableList<Token>, output: MutableList<Expression>) {
        val operator = operators.removeAt(operators.lastIndex)
        val right = output.removeAt(output.lastIndex)
        val left = output.removeAt(output.lastIndex)
        output.add(BinaryOperation(right, left, BinaryOperators.fromSymbol(operator.value)!!, operator.position))
    }
}
