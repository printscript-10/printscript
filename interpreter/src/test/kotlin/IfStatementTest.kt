import interpreter.BooleanVariable
import interpreter.InterpretSuccess
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.IfStatementInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.IfStatement
import utils.Position
import utils.StringLiteral
import utils.VariableAssignation

class IfStatementTest {

    @Test
    fun `test simpleStringVariableAssignation`() {
        val outputProvider = TestOutputProvider()
        val identifierName = "a"
        val conditionalName = "condition"
        val variables: Map<String, Variable> = mutableMapOf(
            identifierName to StringVariable(null, false),
            conditionalName to BooleanVariable(true, false),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val value = StringLiteral(identifierName, position)
        val astNodeVariable = VariableAssignation(id, value, position)
        val conditional = Identifier(conditionalName, position)
        val astNodeIfStatement = IfStatement(conditional, listOf(astNodeVariable), listOf(), position)
        val expectedVariables: Map<String, Variable> = mutableMapOf(
            identifierName to StringVariable("a", false),
            conditionalName to BooleanVariable(true, false),
        )
        val expected = InterpretSuccess(expectedVariables)

        val result = IfStatementInterpreter(variables, outputProvider).execute(astNodeIfStatement)

        assertEquals(expected, result)
    }

    @Test
    fun `test simpleElseVariableAssignation`() {
        val outputProvider = TestOutputProvider()
        val identifierName = "a"
        val conditionalName = "condition"
        val variables: Map<String, Variable> = mutableMapOf(
            identifierName to StringVariable(null, false),
            conditionalName to BooleanVariable(true, false),
        )
        val position = Position(0, 0, 1)
        val id = Identifier(identifierName, position)
        val value = StringLiteral(identifierName, position)
        val astNodeVariable = VariableAssignation(id, value, position)
        val conditional = Identifier(conditionalName, position)
        val astNodeIfStatement = IfStatement(conditional, listOf(astNodeVariable), listOf(), position)
        val expectedVariables: Map<String, Variable> = mutableMapOf(
            identifierName to StringVariable("a", false),
            conditionalName to BooleanVariable(true, false),
        )
        val expected = InterpretSuccess(expectedVariables)

        val result = IfStatementInterpreter(variables, outputProvider).execute(astNodeIfStatement)

        assertEquals(expected, result)
    }
}
