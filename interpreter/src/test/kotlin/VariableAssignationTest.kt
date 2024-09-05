import interpreter.InterpretSuccess
import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.VariableAssignationInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral
import utils.VariableAssignation

class VariableAssignationTest {

    @Test
    fun `test simpleStringVariableAssignation`() {
        val identifierName = "a"
        val variables: MutableMap<String, Variable> = mutableMapOf(
            identifierName to StringVariable(null),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val value = StringLiteral(identifierName, position)
        val astNodeVariable = VariableAssignation(id, value, position)
        val expectedVariable = StringVariable(value.value)
        val expected = InterpretSuccess(mapOf(id.name to expectedVariable))

        val result = VariableAssignationInterpreter(variables).execute(astNodeVariable)

        assertEquals(expected, result)
    }

    @Test
    fun `test simpleNumberVariableAssignation`() {
        val identifierName = "a"
        val variables: MutableMap<String, Variable> = mutableMapOf(
            identifierName to NumericVariable(2),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val value = NumberLiteral(4, position)
        val astNodeVariable = VariableAssignation(id, value, position)
        val expectedVariable = NumericVariable(value.value)
        val expected = InterpretSuccess(mapOf(id.name to expectedVariable))

        val result = VariableAssignationInterpreter(variables).execute(astNodeVariable)

        assertEquals(expected, result)
    }
}
