package astBuilderTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.VariableDeclarationBuilder
import utils.BooleanLiteral
import utils.Identifier
import utils.NumberLiteral
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
        val result = VariableDeclarationBuilder("1.1").build(tokens, 0)
        val expectedResult = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleNumberDeclaration`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "number", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = VariableDeclarationBuilder("1.1").build(tokens, 0)
        val expectedResult = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.NUMBER, dummyPosition),
            NumberLiteral(value = 2, dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleBooleanDeclaration`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "boolean", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.BOOLEAN, value = "true", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = VariableDeclarationBuilder("1.1").build(tokens, 0)
        val expectedResult = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.BOOLEAN, dummyPosition),
            BooleanLiteral(value = true, dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        assertEquals(expected, result)
    }
}
