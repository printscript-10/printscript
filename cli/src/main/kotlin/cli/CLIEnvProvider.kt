package cli

import utils.EnvProvider

class CLIEnvProvider() : EnvProvider {
    override fun getEnv(variable: String): String? {
        return System.getenv(variable)
    }
}
