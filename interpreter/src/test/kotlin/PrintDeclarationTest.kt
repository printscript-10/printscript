import interpreter.InterpretSuccess
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.PrintInterpreter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.Position
import utils.PrintFunction
import utils.StringLiteral

class PrintDeclarationTest {

    // TODO: cambiar este test para que reciba outputProvider
    @Test
    fun `test printOutputsExpectedValue`() {
        val identifierName = "a"
        val variables: MutableMap<String, Variable> = mutableMapOf(
            identifierName to StringVariable("Hello world"),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val value = StringLiteral("Hello world", position)
        val astNodeVariable = PrintFunction(id, position)
        val expectedVariable = StringVariable(value.value)
        val expected = InterpretSuccess(mapOf(id.name to expectedVariable))

        val result = PrintInterpreter(variables).execute(astNodeVariable)

        Assertions.assertEquals(expected, result)
    }
}
