package utils

interface EnvProvider {
    fun getEnv(variable: String): String?
}
