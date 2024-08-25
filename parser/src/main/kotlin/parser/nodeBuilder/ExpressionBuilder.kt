package parser.nodeBuilder

import parser.semanticAnalizer.Success
import utils.BinaryOperation
import utils.Expression
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType

class ExpressionBuilder : ASTNodeBuilder {

    val precedence = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
    )

    val associativity = mapOf(
        "+" to "left",
        "-" to "left",
        "*" to "left",
        "/" to "left",
    )

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val output = mutableListOf<Expression>()
        val operators = mutableListOf<Token>()
        var tokenIndex = 0;

        for (token in tokens) {
            when (token.type) {
                TokenType.NUMBER -> {
                    output.add(NumericLiteralBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.IDENTIFIER -> {
                    output.add(IdentifierBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.STRING -> {
                    output.add(StringLiteralBuilder().build(tokens, tokenIndex).result as Expression)
                }
                TokenType.BINARY_OPERATOR -> {
                    while (operators.isNotEmpty() && shouldPopOperator(operators.last(), token)) {
                        popOperatorToOutput(operators, output, position)
                    }
                    operators.add(token)
                }
                TokenType.OPEN_BRACE -> {
                    operators.add(token)
                }
                TokenType.CLOSE_BRACE -> {
                    while (operators.isNotEmpty() && operators.last().type != TokenType.OPEN_BRACE) {
                        popOperatorToOutput(operators, output, position)
                    }
                    if (operators.isNotEmpty() && operators.last().type == TokenType.OPEN_BRACE) {
                        operators.removeAt(operators.lastIndex)
                    } else {
                        return BuildFailure("Mismatched parentheses", position)
                    }
                }

                else -> {
                    return BuildFailure("Invalid token type: ${token.type} in expression", position)
                }
            }
            tokenIndex++

        }
        while (operators.isNotEmpty()) {
            popOperatorToOutput(operators, output, position)
        }
        // otra cosa q no sea first
        return BuildSuccess(output.first(), position)
    }

    private fun shouldPopOperator(op1: Token, op2: Token): Boolean {
        val precedence1 = precedence[op1.value] ?: throw IllegalArgumentException("Unknown operator: ${op1.value}")
        val precedence2 = precedence[op2.value] ?: throw IllegalArgumentException("Unknown operator: ${op2.value}")
        return precedence1 > precedence2 || (precedence1 == precedence2 && associativity[op2.value] == "left")
    }

    private fun popOperatorToOutput(operators: MutableList<Token>, output: MutableList<Expression>, position: Int) {
        val operator = operators.removeAt(operators.lastIndex)
        val right = output.removeAt(output.lastIndex)
        val left = output.removeAt(output.lastIndex)
        output.add(BinaryOperation(right, left, operator.value, operator.position))
    }
}
