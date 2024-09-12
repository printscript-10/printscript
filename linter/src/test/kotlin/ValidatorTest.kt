import linter.Linter
import linter.LinterConfig
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import utils.Failure
import utils.Identifier
import utils.Position
import utils.Success
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class ValidatorTest {

    @Test
    fun `test validCamelCaseReturnsNull`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("camelCase", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)

        assertTrue(result is Success)
    }

    @Test
    fun `test invalidCamelCaseReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("camel_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)

        val result = linter.execute(variableDeclaration)
        assertTrue(result is Failure)
    }

    @Test
    fun `test validSnakeCaseReturnsNull`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            naming_convention = "snake_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snake_case_identifier", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)

        assertTrue(result is Success)
    }

    @Test
    fun `test invalidSnakeCaseReturnsError`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            naming_convention = "snake_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("snakeCase", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val result = linter.execute(variableDeclaration)

        assertTrue(result is Failure)
    }
}
