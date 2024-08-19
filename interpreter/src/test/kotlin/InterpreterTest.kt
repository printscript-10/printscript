import interpreter.Variable
import interpreter.nodeInterpreter.VariableDeclarationInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.Position
import utils.StringLiteral
import utils.Token
import utils.TokenType
import utils.Type
import utils.VariableDeclaration

class InterpreterTest {

    @Test
    fun `test simpleVariableDeclaration`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);
        val id = Identifier("a", position)
        val value = StringLiteral("a", position)
        val type = Type("string", position)
        val astNodeVariable = VariableDeclaration(id, type, value, position)
        val expectedVariable = Variable(type.name, value.value)
        val expected = mapOf(id.name to expectedVariable)

        val result = VariableDeclarationInterpreter(variables).execute(astNodeVariable)

        assertEquals(expected, result)
    }
}

