package astBuilderTests

import ast.BinaryOperation
import ast.BinaryOperators
import ast.NumberLiteral
import ast.StringLiteral
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.ExpressionBuilder
import position.Position
import token.Token
import token.TokenType

class ExpressionBuilderTest {
    @Test
    fun `test simpleStringExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.STRING, value = "a", dummyPosition),
        )
        val result = ExpressionBuilder("1.0").build(tokens, 0)
        val expectedResult = StringLiteral(value = "a", dummyPosition)
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleNumberExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
        )
        val result = ExpressionBuilder("1.0").build(tokens, 0)
        val expectedResult = NumberLiteral(value = 2, dummyPosition)
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleSumExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "3", dummyPosition),
        )
        val result = ExpressionBuilder("1.0").build(tokens, 0)
        val expectedResult = BinaryOperation(
            right = NumberLiteral(value = 3, dummyPosition),
            left = NumberLiteral(value = 2, dummyPosition),
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test compositeSumExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "3", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "4", dummyPosition),
        )
        val result = ExpressionBuilder("1.0").build(tokens, 0)
        val expectedLeft = BinaryOperation(
            right = NumberLiteral(value = 3, dummyPosition),
            left = NumberLiteral(value = 2, dummyPosition),
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val expectedResult = BinaryOperation(
            right = NumberLiteral(value = 4, dummyPosition),
            left = expectedLeft,
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test compositeMultiplicationExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "*", dummyPosition),
            Token(type = TokenType.NUMBER, value = "3", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "4", dummyPosition),
        )
        val result = ExpressionBuilder("1.0").build(tokens, 0)
        val expectedLeft = BinaryOperation(
            right = NumberLiteral(value = 3, dummyPosition),
            left = NumberLiteral(value = 2, dummyPosition),
            operator = BinaryOperators.TIMES,
            position = dummyPosition,
        )
        val expectedResult = BinaryOperation(
            right = NumberLiteral(value = 4, dummyPosition),
            left = expectedLeft,
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }
}
