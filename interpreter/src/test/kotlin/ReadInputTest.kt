import interpreter.ExpressionFailure
import interpreter.ExpressionSuccess
import interpreter.nodeInterpreter.ReadInputInterpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import utils.Position
import utils.ReadInput
import utils.StringLiteral
import utils.VariableType

class ReadInputTest {
    private val outputProvider = TestOutputProvider()
    private val envProvider = TestEnvProvider()
    private val dummyPosition = Position(0, 0, 1)

    @Test
    fun `should return input when valid input is provided`() {
        val inputProvider = TestInputProvider("Test Input")

        val readInput = ReadInput(
            message = StringLiteral("Please enter something", dummyPosition),
            position = dummyPosition,
        )
        val interpreter = ReadInputInterpreter(
            version = "1.1",
            variables = emptyMap(),
            outputProvider = outputProvider,
            inputProvider = inputProvider,
            envProvider = envProvider,
            expected = VariableType.STRING,
        )

        val result = interpreter.execute(readInput)

        assert(result is ExpressionSuccess)
        assertEquals((result as ExpressionSuccess).value.value, "Test Input")
    }

    @Test
    fun `should return ExpressionFailure when input is empty`() {
        val inputProvider = TestInputProvider()

        val readInput = ReadInput(
            message = StringLiteral("Please enter something", dummyPosition),
            position = dummyPosition,
        )

        val interpreter = ReadInputInterpreter(
            version = "1.1",
            variables = emptyMap(),
            outputProvider = outputProvider,
            inputProvider = inputProvider,
            envProvider = envProvider,
            expected = VariableType.STRING,
        )

        val result = interpreter.execute(readInput)

        assert(result is ExpressionFailure)
    }
}
