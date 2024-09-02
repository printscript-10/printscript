import lexer.LinterFailure
import lexer.LintingError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.PrintFunction
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

// TODO; los tests estos dependen del linter.config.yml q no pinta nada asiq hay q cambiar eso
class LinterTest {

    @Test
    fun `test expressionInPrinterReturnsError`() {
        val linter = Linter()
        val dummyPosition = Position(0, 0, 0)
        val left = NumberLiteral(3, dummyPosition)
        val right = NumberLiteral(4, dummyPosition)
        val operator = BinaryOperators.PLUS
        val printValue = BinaryOperation(right, left, operator, dummyPosition)
        val printFunction = PrintFunction(value = printValue, dummyPosition)
        val result = linter.execute(printFunction)
        val expectedError =
            LinterFailure(listOf(LintingError("Print functions cannot contain expressions", dummyPosition)))
        assertEquals(result, expectedError)
    }

    @Test
    fun `test violationInNamingConvetionReturnsError`() {
        val linter = Linter()
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snake_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, dummyPosition)
        val result = linter.execute(variableDeclaration)
        val expectedError = LinterFailure(listOf(LintingError("Identifier does not match camel_case", dummyPosition)))
        assertEquals(result, expectedError)
    }
}
