import formatter.FormatSuccess
import formatter.Formatter
import formatter.FormatterConfig
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.PrintFunction
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableAssignation
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

class FormatterTest {

    @Test
    fun `test formatVariableDeclaration-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
        )
        val formatter = Formatter(config)
        val tokens = listOf(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val ast = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "let a : string = \"b\";\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatVariableDeclaration-2`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = false,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
        )
        val formatter = Formatter(config)
        val tokens = listOf(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val ast = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "let a :string = \"b\";\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatVariableDeclaration-3`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = false,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
        )
        val formatter = Formatter(config)
        val tokens = listOf(
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val ast = VariableDeclaration(
            Identifier(name = "a", dummyPosition),
            Type(name = VariableType.STRING, dummyPosition),
            StringLiteral(value = "b", dummyPosition),
            isFinal = false,
            dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "let a: string = \"b\";\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatVariableAssignation-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = false,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
        )
        val formatter = Formatter(config)
        val tokens = listOf(
            Token(type = TokenType.IDENTIFIER, value = "a", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val ast = VariableAssignation(
            Identifier(name = "a", dummyPosition),
            value = StringLiteral("b", dummyPosition),
            dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "a = \"b\";\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatPrintFunction-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = false,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 2,
        )
        val formatter = Formatter(config)
        val tokens = listOf<Token>(
            Token(type = TokenType.PRINT, value = "println", dummyPosition),
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.STRING, value = "b", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val ast = PrintFunction(
            value = StringLiteral("b", dummyPosition),
            dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "\n\nprintln(\"b\");\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatBinaryOperation-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = false,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 2,
        )
        val formatter = Formatter(config)
        val tokens = listOf<Token>(
            Token(type = TokenType.OPEN_BRACKET, value = "(", dummyPosition),
            Token(type = TokenType.NUMBER, value = "2", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "3", dummyPosition),
            Token(type = TokenType.CLOSE_BRACKET, value = ")", dummyPosition),
            Token(type = TokenType.BINARY_OPERATOR, value = "+", dummyPosition),
            Token(type = TokenType.NUMBER, value = "4", dummyPosition),
            Token(type = TokenType.SEMICOLON, value = ";", dummyPosition),
        )
        val leftSide = BinaryOperation(
            right = NumberLiteral(value = 3, dummyPosition),
            left = NumberLiteral(value = 2, dummyPosition),
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val ast = BinaryOperation(
            right = NumberLiteral(value = 4, dummyPosition),
            left = leftSide,
            operator = BinaryOperators.PLUS,
            position = dummyPosition,
        )
        val result = (formatter.format(tokens, ast) as FormatSuccess).result
        val expected = "(2 + 3) + 4;\n"
        assertEquals(expected, result)
    }
}
