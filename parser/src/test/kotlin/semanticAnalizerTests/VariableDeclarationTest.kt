package semanticAnalizerTests

import org.junit.jupiter.api.Test
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.Identifier
import utils.Position
import utils.StringLiteral
import utils.Type
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

class VariableDeclarationTest {
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
        val variableDeclaration = VariableDeclaration(identifier, type, value, isFinal = false, dummyPosition)
        val expectedResult = Success(updatedVariables)

        val result = semanticAnalyzer.analyze(variableDeclaration)

        assertEquals(result, expectedResult)
    }
}
