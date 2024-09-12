import formatter.FormatApplicatorSuccess
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

class IfStatementFormatterTest {

    @Test
    fun `test formatIfStatement-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
            if_block_indent_spaces = 1,
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
        val expected = "if (a) {\n let b : string = \"b\";\n}\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatIfStatement-2`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
            if_block_indent_spaces = 1,
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
            Token(type = TokenType.VARIABLE_DECLARATOR, value = "let", dummyPosition),
            Token(type = TokenType.IDENTIFIER, value = "c", dummyPosition),
            Token(type = TokenType.COLON, value = ":", dummyPosition),
            Token(type = TokenType.TYPE, value = "string", dummyPosition),
            Token(type = TokenType.ASSIGN, value = "=", dummyPosition),
            Token(type = TokenType.STRING, value = "c", dummyPosition),
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
            VariableDeclaration(
                Identifier(name = "c", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
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
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
        val expected = "if (a) {\n let b : string = \"b\";\n let c : string = \"c\";\n}\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatNestedIfStatement-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
            if_block_indent_spaces = 1,
        )
        val formatter = Formatter(config)
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
        val ast = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatements,
            elseStatements = listOf(),
            position = dummyPosition,
        )
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
        val expected = "if (a) {\n if (a) {\n  let b : string = \"b\";\n }\n}\n"
        assertEquals(expected, result)
    }

    @Test
    fun `test formatIfElseStatement-1`() {
        val dummyPosition = Position(0, 0, 0)
        val config = FormatterConfig(
            declaration_colon_leading_whitespaces = true,
            declaration_colon_trailing_whitespaces = true,
            assignation_equal_wrap_whitespaces = true,
            println_trailing_line_jump = 1,
            if_block_indent_spaces = 1,
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
        val ast = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val tokensResult = (formatter.format(tokens, ast) as FormatApplicatorSuccess)
        val result = formatter.concatenateTokenValues(tokensResult.tokens)
        val expected = "if (a) {\n let b : string = \"b\";\n} else {\n let b : string = \"c\";\n}\n"
        assertEquals(expected, result)
    }
}
