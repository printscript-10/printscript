import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import parser.nodeBuilder.BuildSuccess
import parser.nodeBuilder.ExpressionBuilder
import parser.nodeBuilder.IfStatementBuilder
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Identifier
import utils.IfStatement
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableAssignation
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

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
        val result = IfStatementBuilder().build(tokens, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "a", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                dummyPosition,
            )
        )
        val expectedResult = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = null,
            position = dummyPosition,
        )
        val expected = BuildSuccess(result = expectedResult, 0)
        Assertions.assertEquals(expected, result)
    }
}
