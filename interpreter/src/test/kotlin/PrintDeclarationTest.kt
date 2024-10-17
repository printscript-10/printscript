import ast.Identifier
import ast.PrintFunction
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.PrintInterpreter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import position.Position

class PrintDeclarationTest {
    private val outputProvider = TestOutputProvider()
    private val inputProvider = TestInputProvider()
    private val envProvider = TestEnvProvider()

    @Test
    fun `test printOutputsExpectedValue`() {
        val identifierName = "a"
        val variables: MutableMap<String, Variable> = mutableMapOf(
            identifierName to StringVariable("Hello world", false),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val astNodeVariable = PrintFunction(id, position)

        PrintInterpreter(
            "1.0",
            variables,
            outputProvider,
            inputProvider,
            envProvider,
        ).execute(astNodeVariable)

        Assertions.assertEquals(outputProvider.output, variables[identifierName]?.value)
    }
}
