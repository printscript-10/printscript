import interpreter.Variable
import interpreter.nodeInterpreter.ExpressionInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral

class BinaryOperationTest {
    @Test
    fun `test simpleSum`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);
        val left = NumberLiteral(3, position)
        val right = NumberLiteral(4, position)
        val operator = "+"
        val sum = BinaryOperation(right, left, operator, position)
        val expected = Variable(type= "number", value = "7.0")

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

        val expected = Variable(type= "number", value = "4.0")
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test twoStringConcatWithSum`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);

        val leftSub = StringLiteral("a", position)
        val rightSub = StringLiteral("1", position)
        val operator = "+"
        val sum = BinaryOperation(rightSub, leftSub, operator, position)

        val expected = Variable(type= "string", value = "a1")
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test oneStringOneNumberConcat`() {
        val variables: Map<String, Variable> = mapOf();
        val position = Position(0, 0, 1);

        val leftSub = StringLiteral("a", position)
        val rightSub = NumberLiteral(1, position)
        val operator = "+"
        val sum = BinaryOperation(rightSub, leftSub, operator, position)

        val expected = Variable(type= "string", value = "a1")
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }
}

