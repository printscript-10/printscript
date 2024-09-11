import lexer.linter.LinterFailure
import linter.Linter
import linter.LinterConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Failure
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.PrintFunction
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class LinterTest {

    @Test
    fun `test expressionInPrinterReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig)
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
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig)
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snake_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)
        assertTrue(result is Failure)
    }
}
