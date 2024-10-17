import ast.BinaryOperation
import ast.BinaryOperators
import ast.Identifier
import ast.NumberLiteral
import ast.PrintFunction
import ast.Type
import ast.VariableDeclaration
import ast.VariableType
import linter.Linter
import linter.LinterConfig
import linter.LinterFailure
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import position.Position
import result.Failure

class LinterTest {

    @Test
    fun `test expressionInPrinterReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            allow_expression_in_readinput = null,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val left = NumberLiteral(3, dummyPosition)
        val right = NumberLiteral(4, dummyPosition)
        val operator = BinaryOperators.PLUS
        val printValue = BinaryOperation(right, left, operator, dummyPosition)
        val printFunction = PrintFunction(value = printValue, dummyPosition)
        val result = linter.execute(printFunction)
        val expectedError = LinterFailure("Print functions cannot contain expressions")
        assertEquals(result, expectedError)
    }

    @Test
    fun `test violationInNamingConvetionReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            allow_expression_in_readinput = null,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snake_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)
        assertTrue(result is Failure)
    }

    @Test
    fun `test violationInReadInputReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            allow_expression_in_readinput = false,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snake_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)
        assertTrue(result is Failure)
    }
}
