package provider

interface InputProvider {
    fun readInput(name: String): String?
}
