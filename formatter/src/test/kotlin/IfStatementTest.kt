import formatter.FormatSuccess
import formatter.Formatter
import formatter.FormatterConfig
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.IfStatement
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

class IfStatementTest {

    @Test
    fun `test formatIfStatement-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
            if_block_indent_spaces = 1
        )
        val formatter = Formatter(config)
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
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                isFinal = false,
                dummyPosition,
            ),
        )
        val ast = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = listOf(),
            position = dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "if(a){\n let a : string = \"b\";\n}\n"
        assertEquals(expected, result)
    }
}
