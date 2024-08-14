import lexer.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Position
import utils.Token
import utils.TokenType

class LexerTests {

    @Test
    fun `test simpleStringDeclaration`() {
        val input = "let a: string = \"b\";"
        val result = Lexer().tokenize(input)
        val expected = listOf(
            Token(position = Position(0, 0, 3), type = TokenType.VARIABLE_DECLARATOR, value = "let"),
            Token(position = Position(0, 4, 5), type = TokenType.IDENTIFIER, value = "a"),
            Token(position = Position(0, 5, 6), type = TokenType.COLON, value = ":"),
            Token(position = Position(0, 7, 13), type = TokenType.TYPE, value = "string"),
            Token(position = Position(0, 14, 15), type = TokenType.ASSIGN, value = "="),
            Token(position = Position(0, 16, 19), type = TokenType.STRING, value = "\"b\""),
            Token(position = Position(0, 19, 20), type = TokenType.SEMICOLON, value = ";"),
        )

        assertEquals(expected, result)
    }
}
