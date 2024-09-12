package cli

import utils.InputProvider

class CLIInputProvider : InputProvider {
    override fun readInput(): String? {
        return readlnOrNull()
    }
}
