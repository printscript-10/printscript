import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.BinaryOperation
import utils.BinaryOperators
import utils.Identifier
import utils.NumberLiteral
import utils.Position
import utils.PrintFunction
import utils.StringLiteral
import utils.Type
import utils.VariableAssignation
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

class SemanticAnalizerTest {

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

    @Test
    fun `test variableDeclaration`() {
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables: Map<String, VariableType> = mapOf()
        val updatedVariables = mapOf(
            variableName to VariableType.STRING,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)
        val identifier = Identifier(variableName, dummyPosition)
        val value = StringLiteral("random value", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, value, dummyPosition)
        val expectedResult = Success(updatedVariables)

        val result = semanticAnalyzer.analyze(variableDeclaration)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `test printDeclaration`() {
        val variableName = "a"
        val dummyPosition = Position(0, 0, 0)
        val variables = mapOf(
            variableName to VariableType.STRING,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)
        val identifier = Identifier(variableName, dummyPosition)
        val printFunction = PrintFunction(identifier, dummyPosition)
        val expectedResult = Success(variables)

        val result = semanticAnalyzer.analyze(printFunction)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `test invalidBinaryOperationReturnsError`() {
        val dummyPosition = Position(0, 0, 0)
        val binaryOperation = BinaryOperation(
            right = NumberLiteral(2, dummyPosition),
            left = StringLiteral("test", dummyPosition),
            operator = BinaryOperators.MINUS,
            position = dummyPosition,
        )
        val variables: Map<String, VariableType> = mapOf()
        val semanticAnalyzer = SemanticAnalyzer(variables)
        val printFunction = PrintFunction(binaryOperation, dummyPosition)

        val result = semanticAnalyzer.analyze(printFunction)

        assertTrue(result is Failure)
    }
}
