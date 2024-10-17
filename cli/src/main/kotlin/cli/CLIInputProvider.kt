package cli

import provider.InputProvider

class CLIInputProvider : InputProvider {
    override fun readInput(message: String): String? {
        return readlnOrNull()
    }
}
