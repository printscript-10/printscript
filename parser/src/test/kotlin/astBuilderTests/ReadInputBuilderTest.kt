package astBuilderTests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildFailure
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.ReadInputBuilder
import utils.Position
import utils.ReadInput
import utils.StringLiteral
import utils.Token
import utils.TokenType
import kotlin.test.assertEquals

class ReadInputBuilderTest {

    private val version = "1.1"
    private val builder = ReadInputBuilder(version)
    private val dummyPosition = Position(0, 0, 1)

    @Test
    fun `should successfully build ReadInput with valid tokens`() {
        val tokens = listOf(
            Token(TokenType.READ_INPUT, "readInput", dummyPosition),
            Token(TokenType.OPEN_BRACKET, "(", dummyPosition),
            Token(TokenType.STRING, "Enter value", dummyPosition),
            Token(TokenType.CLOSE_BRACKET, ")", dummyPosition),
        )

        val result = builder.build(tokens, 0)

        assertTrue(result is BuildSuccess)
        val readInput = (result as BuildSuccess).result as ReadInput
        assertTrue(readInput.message is StringLiteral)
        assertEquals((readInput.message as StringLiteral).value, "Enter value")
    }

    @Test
    fun `should return BuildFailure for mismatched parentheses`() {
        val tokens = listOf(
            Token(TokenType.READ_INPUT, "readInput", dummyPosition),
            Token(TokenType.OPEN_BRACKET, "(", dummyPosition),
            Token(TokenType.STRING, "Enter value", dummyPosition),
        )

        val result = builder.build(tokens, 0)

        assertTrue(result is BuildFailure)
    }

    @Test
    fun `should return BuildFailure for empty ReadInput`() {
        val tokens = listOf(
            Token(TokenType.READ_INPUT, "readInput", dummyPosition),
            Token(TokenType.OPEN_BRACKET, "(", dummyPosition),
            Token(TokenType.CLOSE_BRACKET, ")", dummyPosition),
        )

        val result = builder.build(tokens, 0)

        assertTrue(result is BuildFailure)
    }
}
