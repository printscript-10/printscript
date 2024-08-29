import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.VariableDeclarationBuilder
import utils.Identifier
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class VariableDeclarationBuilderTest {

    @Test
    fun `test simpleStringDeclaration`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = VariableDeclarationBuilder().build(tokens, 0)
        val expectedResult = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }
}
