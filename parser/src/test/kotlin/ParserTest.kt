import ast.Identifier
import ast.PrintFunction
import ast.StringLiteral
import ast.Type
import ast.VariableAssignation
import ast.VariableDeclaration
import ast.VariableType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.ParseSuccess
import parser.Parser
import position.Position
import token.Token
import token.TokenType

class ParserTest {

    @Test
    fun `test variableDeclaration`() {
        val version = "1.0"
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables: Map<String, VariableType> = mapOf()
        val updatedVariables = mapOf(
            variableName to VariableType.STRING,
        )
        val tokens = listOf<Token>(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = Parser(version, variables).buildAST(tokens)
        val expectedResult = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val expected = ParseSuccess(result = expectedResult, updatedVariables)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `test variableAssignation`() {
        val version = "1.0"
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables = mapOf(
            variableName to VariableType.STRING,
        )
        val tokens = listOf<Token>(
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = Parser(version, variables).buildAST(tokens)
        val expectedResult = VariableAssignation(
            Identifier(name = "a", dummyPosition),
            value = StringLiteral("b", dummyPosition),
            dummyPosition,
        )
        val expected = ParseSuccess(result = expectedResult, variables)
        Assertions.assertEquals(expected, result)
    }

    @Test
    fun `test printDeclaration`() {
        val version = "1.0"
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables = mapOf(
            variableName to VariableType.STRING,
        )
        val tokens = listOf<Token>(
            Token(type = TokenType.PRINT, value = "println", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val result = Parser(version, variables).buildAST(tokens)
        val expectedResult = PrintFunction(
            value = StringLiteral("b", dummyPosition),
            dummyPosition,
        )
        val expected = ParseSuccess(result = expectedResult, variables)
        Assertions.assertEquals(expected, result)
    }
}
