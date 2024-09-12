package astBuilderTests

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.IfStatementBuilder
import utils.Identifier
import utils.IfStatement
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class IfStatementBuilderTest {

    @Test
    fun `test simpleIfDeclarationWithNoElse`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.IF, value = "if", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.OPEN_BRACE, value = "{", dummyPosition),
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "b", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
            Token(type = TokenType.CLOSE_BRACE, value = "}", dummyPosition),
        )
        val result = IfStatementBuilder("1.1").build(tokens, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                isFinal = false,
                dummyPosition,
            ),
        )
        val expectedResult = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = listOf(),
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `test simpleIfDeclarationWithElse`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.IF, value = "if", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.OPEN_BRACE, value = "{", dummyPosition),
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "b", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
            Token(type = TokenType.CLOSE_BRACE, value = "}", dummyPosition),
            Token(type = TokenType.ELSE, value = "else", dummyPosition),
            Token(type = TokenType.OPEN_BRACE, value = "{", dummyPosition),
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "b", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "c", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
            Token(type = TokenType.CLOSE_BRACE, value = "}", dummyPosition),
        )
        val result = IfStatementBuilder("1.1").build(tokens, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                isFinal = false,
                dummyPosition,
            ),
        )
        val expectedElseStatement = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
                isFinal = false,
                dummyPosition,
            ),
        )
        val expectedResult = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `test nestedIfDeclaration`() {
        val dummyPosition = Position(0, 0, 0)
        val tokens = listOf<Token>(
            Token(type = TokenType.IF, value = "if", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.OPEN_BRACE, value = "{", dummyPosition),
            Token(type = TokenType.IF, value = "if", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.OPEN_BRACE, value = "{", dummyPosition),
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "b", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
            Token(type = TokenType.CLOSE_BRACE, value = "}", dummyPosition),
            Token(type = TokenType.CLOSE_BRACE, value = "}", dummyPosition),
        )
        val result = IfStatementBuilder("1.1").build(tokens, 0)
        val expectedInnerThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                isFinal = false,
                dummyPosition,
            ),
        )
        val expectedThenStatements = listOf(
            IfStatement(
                condition = Identifier("a", dummyPosition),
                thenStatements = expectedInnerThenStatemets,
                elseStatements = listOf(),
                position = dummyPosition,
            ),
        )
        val expectedResult = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatements,
            elseStatements = listOf(),
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        Assertions.assertEquals(expected, result)
    }
}
