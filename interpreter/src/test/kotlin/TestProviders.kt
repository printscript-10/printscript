import provider.EnvProvider
import provider.InputProvider
import provider.OutputProvider

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
