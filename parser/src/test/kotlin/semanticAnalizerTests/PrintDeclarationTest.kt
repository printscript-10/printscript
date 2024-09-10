package semanticAnalizerTests

import org.junit.jupiter.api.Test
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.Identifier
import utils.Position
import utils.PrintFunction
import utils.VariableType
import kotlin.test.assertEquals

class PrintDeclarationTest {
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
}
