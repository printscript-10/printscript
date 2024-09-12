import linter.Linter
import linter.LinterConfig
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import utils.BooleanLiteral
import utils.Failure
import utils.Identifier
import utils.IfStatement
import utils.Position
import utils.Success
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class IfStatementLinterTest {

    @Test
    fun `test violationInIfStatementReturnsError`() {
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
        val ifStatement = IfStatement(
            condition = BooleanLiteral(true, dummyPosition),
            thenStatements = listOf(variableDeclaration),
            elseStatements = listOf(),
            dummyPosition,
        )
        val result = linter.execute(ifStatement)
        assertTrue(result is Failure)
    }

    @Test
    fun `test noViolationInIfStatementReturnsSuccess`() {
        val linterConfig = LinterConfig(
            allow_expression_in_println = false,
            allow_expression_in_readinput = false,
            naming_convention = "camel_case",
        )
        val linter = Linter(linterConfig, "1.1")
        val dummyPosition = Position(0, 0, 0)
        val identifier = Identifier("camelCase", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, isFinal = false, dummyPosition)
        val ifStatement = IfStatement(
            condition = BooleanLiteral(true, dummyPosition),
            thenStatements = listOf(variableDeclaration),
            elseStatements = listOf(),
            dummyPosition,
        )
        val result = linter.execute(ifStatement)
        assertTrue(result is Success)
    }
}
