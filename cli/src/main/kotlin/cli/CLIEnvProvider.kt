package cli

import provider.EnvProvider

class CLIEnvProvider() : EnvProvider {
    override fun getEnv(variable: String): String? {
        return System.getenv(variable)
    }
}
