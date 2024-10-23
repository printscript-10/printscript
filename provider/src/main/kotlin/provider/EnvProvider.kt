package provider

interface EnvProvider {
    fun getEnv(variable: String): String?
}
