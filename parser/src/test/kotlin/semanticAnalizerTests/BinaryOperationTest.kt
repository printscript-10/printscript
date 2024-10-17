package semanticAnalizerTests

import ast.BinaryOperation
import ast.BinaryOperators
import ast.NumberLiteral
import ast.PrintFunction
import ast.StringLiteral
import ast.VariableType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.SemanticAnalyzer
import position.Position

class BinaryOperationTest {
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
