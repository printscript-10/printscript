import utils.EnvProvider
import utils.InputProvider
import utils.OutputProvider

class TestOutputProvider : OutputProvider {
    var output: String? = null

    override fun print(message: String) {
        output = message
    }
}

class TestInputProvider(var input: String? = null) : InputProvider {
    override fun readInput(message: String): String? {
        return input
    }
}

class TestEnvProvider : EnvProvider {
    override fun getEnv(variable: String): String? {
        return ""
    }
}
