import interpreter.InterpretSuccess
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.Position
import utils.StringLiteral
import utils.Type
import utils.VariableDeclaration
import utils.VariableType

class VariableDeclarationTest {
    private val outputProvider = TestOutputProvider()
    private val inputProvider = TestInputProvider()
    private val envProvider = TestEnvProvider()

    @Test
    fun `test simpleVariableDeclaration`() {
        val variables: MutableMap<String, Variable> = mutableMapOf()
        val position = Position(0, 0, 1)
        val id = Identifier("a", position)
        val value = StringLiteral("a", position)
        val type = Type(VariableType.STRING, position)
        val astNodeVariable = VariableDeclaration(id, type, value, isFinal = false, position)
        val expectedVariable = StringVariable(value.value, false)
        val expected = InterpretSuccess(mapOf(id.name to expectedVariable))

        val result = VariableDeclarationInterpreter(
            "1.0",
            variables,
            outputProvider,
            inputProvider,
            envProvider,
        ).execute(astNodeVariable)

        assertEquals(expected, result)
    }
}
