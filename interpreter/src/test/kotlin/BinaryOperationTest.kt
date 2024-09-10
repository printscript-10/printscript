import interpreter.NumericVariable
import interpreter.StringVariable
import interpreter.Variable
import interpreter.nodeInterpreter.ExpressionInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.BinaryOperation
import utils.BinaryOperators
import utils.NumberLiteral
import utils.Position
import utils.StringLiteral

class BinaryOperationTest {
    @Test
    fun `test simpleSum`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)
        val left = NumberLiteral(3, position)
        val right = NumberLiteral(4, position)
        val operator = BinaryOperators.PLUS
        val sum = BinaryOperation(right, left, operator, position)
        val expected = NumericVariable(value = 7, false)

        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test sumWithSubstraction`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)

        val leftSub = NumberLiteral(5, position)
        val rightSub = NumberLiteral(4, position)
        val subOperator = BinaryOperators.MINUS
        val sub = BinaryOperation(rightSub, leftSub, subOperator, position)

        val leftSum = NumberLiteral(3, position)
        val sumOperator = BinaryOperators.MINUS
        val sum = BinaryOperation(sub, leftSum, sumOperator, position)

        val expected = NumericVariable(value = 2, false)
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test twoStringConcatWithSum`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)

        val leftSub = StringLiteral("a", position)
        val rightSub = StringLiteral("1", position)
        val operator = BinaryOperators.PLUS
        val sum = BinaryOperation(rightSub, leftSub, operator, position)

        val expected = StringVariable(value = "a1", false)
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test oneStringOneNumberConcat`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)

        val leftSub = StringLiteral("a", position)
        val rightSub = NumberLiteral(1, position)
        val operator = BinaryOperators.PLUS
        val sum = BinaryOperation(rightSub, leftSub, operator, position)

        val expected = StringVariable(value = "a1", false)
        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleMultiplication`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)
        val left = NumberLiteral(3, position)
        val right = NumberLiteral(4, position)
        val operator = BinaryOperators.TIMES
        val sum = BinaryOperation(right, left, operator, position)
        val expected = NumericVariable(value = 12, false)

        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }

    @Test
    fun `test simpleDivision`() {
        val variables: Map<String, Variable> = mapOf()
        val position = Position(0, 0, 1)
        val left = NumberLiteral(4, position)
        val right = NumberLiteral(2, position)
        val operator = BinaryOperators.DIV
        val sum = BinaryOperation(right, left, operator, position)
        val expected = NumericVariable(value = 2, false)

        val result = ExpressionInterpreter(variables).execute(sum)
        assertEquals(expected, result)
    }
}
