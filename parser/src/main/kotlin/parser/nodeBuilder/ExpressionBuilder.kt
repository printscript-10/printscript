package parser.nodeBuilder

import utils.BinaryOperation
import utils.BinaryOperators
import utils.Expression
import utils.Result
import utils.Token
import utils.TokenType

class ExpressionBuilder(version: String) : ASTNodeBuilder {

    private val precedence = mapOf(
        BinaryOperators.PLUS to 1,
        BinaryOperators.MINUS to 1,
        BinaryOperators.TIMES to 2,
        BinaryOperators.DIV to 2,
    )
    private val builders: Map<TokenType, ASTNodeBuilder>

    init {
        builders = getBuilders(version)
    }

    override fun build(tokens: List<Token>, position: Int): Result {
        val output = mutableListOf<Expression>()
        val operators = mutableListOf<Token>()

        var tokenIndex = 0
        while (tokenIndex < tokens.size) {
            val token = tokens[tokenIndex]

            if (builders.containsKey(token.type)) {
                val result = builders[token.type]?.build(tokens, tokenIndex) as BuildSuccess
                output.add(result.result as Expression)
                tokenIndex = result.position
            } else {
                when (token.type) {
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
                            return BuildFailure("Mismatched parentheses")
                        }
                    }

                    else -> {
                        return BuildFailure("Invalid token type: ${token.type} in expression at position")
                    }
                }
            }
            tokenIndex++
        }

        while (operators.isNotEmpty()) {
            if (operators.last().type == TokenType.OPEN_BRACKET) {
                return BuildFailure("Mismatched parentheses")
            }
            popOperatorToOutput(operators, output)
        }

        return BuildSuccess(output.first(), position)
    }

    private fun shouldPopOperator(op1: Token, op2: Token): Boolean {
        if (op1.type == TokenType.OPEN_BRACKET || op2.type == TokenType.OPEN_BRACKET) return false
        val precedence1 = precedence[BinaryOperators.fromSymbol(op1.value)]!!
        val precedence2 = precedence[BinaryOperators.fromSymbol(op2.value)]!!
        return precedence1 > precedence2 || (precedence1 == precedence2)
    }

    private fun popOperatorToOutput(operators: MutableList<Token>, output: MutableList<Expression>) {
        val operator = operators.removeAt(operators.lastIndex)
        val right = output.removeAt(output.lastIndex)
        val left = output.removeAt(output.lastIndex)
        output.add(BinaryOperation(right, left, BinaryOperators.fromSymbol(operator.value)!!, operator.position))
    }

    private fun getBuilders(version: String): Map<TokenType, ASTNodeBuilder> {
        val baseMap: Map<TokenType, ASTNodeBuilder> = mapOf(
            TokenType.NUMBER to NumericLiteralBuilder(),
            TokenType.IDENTIFIER to IdentifierBuilder(),
            TokenType.STRING to StringLiteralBuilder(),
        )
        return when (version) {
            "1.0" -> baseMap
            "1.1" -> {
                baseMap.toMutableMap().apply {
                    putAll(
                        mapOf(
                            TokenType.BOOLEAN to BooleanLiteralBuilder(),
                            TokenType.READ_ENV to ReadEnvBuilder(),
                            TokenType.READ_INPUT to ReadInputBuilder(version),
                        ),
                    )
                }.toMap()
            }
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
