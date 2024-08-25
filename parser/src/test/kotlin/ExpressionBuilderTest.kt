import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.ExpressionBuilder
import utils.BinaryOperation
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType

class ExpressionBuilderTest {

    @Test
    fun `test simpleStringExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.STRING, value = "a", dummyPosition),
        )
        val result = ExpressionBuilder().build(tokens, 0)
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
        val result = ExpressionBuilder().build(tokens, 0)
        val expectedResult = NumberLiteral(value = 2.0, dummyPosition)
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
        val result = ExpressionBuilder().build(tokens, 0)
        val expectedResult = BinaryOperation(
            right = NumberLiteral(value = 2.0, dummyPosition),
            left = NumberLiteral(value = 3.0, dummyPosition),
            operator = "+",
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test compositeSumExpression`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "3", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "4", dummyPosition),
        )
        val result = ExpressionBuilder().build(tokens, 0)
        val expectedRight = BinaryOperation(
            right = NumberLiteral(value = 2.0, dummyPosition),
            left = NumberLiteral(value = 3.0, dummyPosition),
            operator = "+",
            position = dummyPosition,
        )
        val expectedResult = BinaryOperation(
            right = expectedRight,
            left = NumberLiteral(value = 4.0, dummyPosition),
            operator = "+",
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }
}
