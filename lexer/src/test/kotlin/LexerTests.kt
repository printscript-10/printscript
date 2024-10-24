import lexer.Lexer
import lexer.LexingFailure
import lexer.LexingSuccess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import position.Position
import token.Token
import token.TokenType

class LexerTests {

    @Test
    fun `test simpleStringDeclaration`() {
        val input = "let a: string = \"b\";"
        val result = Lexer("1.0").tokenize(input, 0)
        val expected = LexingSuccess(
            listOf(
                Token(position = Position(0, 0, 3), type = TokenType.VARIABLE_DECLARATOR, value = "let"),
                Token(position = Position(0, 4, 5), type = TokenType.IDENTIFIER, value = "a"),
                Token(position = Position(0, 5, 6), type = TokenType.COLON, value = ":"),
                Token(position = Position(0, 7, 13), type = TokenType.TYPE, value = "string"),
                Token(position = Position(0, 14, 15), type = TokenType.ASSIGN, value = "="),
                Token(position = Position(0, 16, 19), type = TokenType.STRING, value = "b"),
                Token(position = Position(0, 19, 20), type = TokenType.SEMICOLON, value = ";"),
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test unknownTokenReturnsFailure`() {
        val input = "let a: string = &%&;"
        val result = Lexer("1.0").tokenize(input, 0)

        assertTrue(result is LexingFailure)
    }

    @Test
    fun `test simpleBooleanDeclaration`() {
        val input = "let a: boolean = true;"
        val result = Lexer("1.1").tokenize(input, 0)
        val expected = LexingSuccess(
            listOf(
                Token(position = Position(0, 0, 3), type = TokenType.VARIABLE_DECLARATOR, value = "let"),
                Token(position = Position(0, 4, 5), type = TokenType.IDENTIFIER, value = "a"),
                Token(position = Position(0, 5, 6), type = TokenType.COLON, value = ":"),
                Token(position = Position(0, 7, 14), type = TokenType.TYPE, value = "boolean"),
                Token(position = Position(0, 15, 16), type = TokenType.ASSIGN, value = "="),
                Token(position = Position(0, 17, 21), type = TokenType.BOOLEAN, value = "true"),
                Token(position = Position(0, 21, 22), type = TokenType.SEMICOLON, value = ";"),
            ),
        )
        assertEquals(expected, result)
    }
}
