import ast.BinaryOperation
import ast.BinaryOperators
import ast.Identifier
import ast.NumberLiteral
import ast.PrintFunction
import ast.StringLiteral
import ast.Type
import ast.VariableAssignation
import ast.VariableDeclaration
import ast.VariableType
import formatter.FormatApplicatorSuccess
import formatter.Formatter
import formatter.FormatterConfig
import org.junit.jupiter.api.Test
import position.Position
import token.Token
import token.TokenType
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
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
        val formatter = Formatter(config, "1.0")
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
        val expected = "(2 + 3) + 4;\n"
        assertEquals(expected, result)
    }
}
