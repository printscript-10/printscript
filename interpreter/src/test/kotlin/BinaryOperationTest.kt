import interpreter.Variable
import interpreter.nodeInterpreter.ExpressionInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.NumberLiteral
import utils.Position

class BinaryOperationTest {

    @Test
    fun `test simpleSum`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);
        val left = NumberLiteral(3, position)
        val right = NumberLiteral(4, position)
        val operator = "+"
        val sum = BinaryOperation(right, left, operator, position)
        val expected = "7.0"

        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test sumWithSubstraction`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);

        val leftSub = NumberLiteral(5, position)
        val rightSub = NumberLiteral(4, position)
        val subOperator = "-"
        val sub = BinaryOperation(rightSub, leftSub, subOperator, position)

        val leftSum = NumberLiteral(3, position)
        val sumOperator = "+"
        val sum = BinaryOperation(sub, leftSum, sumOperator, position)

        val expected = "4.0"
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }
}

