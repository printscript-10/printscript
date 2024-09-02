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

    @Test
    fun `test simpleVariableDeclaration`() {
        val variables: MutableMap<String, Variable> = mutableMapOf()
        val position = Position(0, 0, 1)
        val id = Identifier("a", position)
        val value = StringLiteral("a", position)
        val type = Type(VariableType.STRING, position)
        val astNodeVariable = VariableDeclaration(id, type, value, position)
        val expectedVariable = StringVariable(value.value)
        val expected = InterpretSuccess(mapOf(id.name to expectedVariable))

        val result = VariableDeclarationInterpreter(variables).execute(astNodeVariable)

        assertEquals(expected, result)
    }
}
