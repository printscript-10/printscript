package semanticAnalizerTests

import ast.Identifier
import ast.StringLiteral
import ast.VariableAssignation
import ast.VariableType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import position.Position
import kotlin.test.assertEquals

class VariableAssignationTest {

    @Test
    fun `test noViolationsReturnsSuccess`() {
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables = mapOf(
            variableName to VariableType.STRING,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)
        val identifier = Identifier(variableName, dummyPosition)
        val value = StringLiteral("random value", dummyPosition)
        val variableAssignation = VariableAssignation(identifier, value, dummyPosition)
        val expectedResult = Success(variables)

        val result = semanticAnalyzer.analyze(variableAssignation)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `test typeViolationReturnsFailure`() {
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables = mapOf(
            variableName to VariableType.NUMBER,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)
        val identifier = Identifier(variableName, dummyPosition)
        val value = StringLiteral("random value", dummyPosition)
        val variableAssignation = VariableAssignation(identifier, value, dummyPosition)

        val result = semanticAnalyzer.analyze(variableAssignation)

        assertTrue(result is Failure)
    }
}
