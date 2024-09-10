import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.PrintInterpreter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.Position
import utils.PrintFunction

class PrintDeclarationTest {
    @Test
    fun `test printOutputsExpectedValue`() {
        val identifierName = "a"
        val outputProvider = TestOutputProvider()
        val variables: MutableMap<String, Variable> = mutableMapOf(
            identifierName to StringVariable("Hello world", false),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val astNodeVariable = PrintFunction(id, position)

        PrintInterpreter(variables, outputProvider).execute(astNodeVariable)

        Assertions.assertEquals(outputProvider.output, variables[identifierName]?.value)
    }
}
