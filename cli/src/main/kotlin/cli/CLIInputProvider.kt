package cli

import utils.InputProvider

class CLIInputProvider : InputProvider {
    override fun readInput(message: String): String? {
        return readlnOrNull()
    }
}
