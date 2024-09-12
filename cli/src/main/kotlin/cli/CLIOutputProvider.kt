package cli

import utils.OutputProvider

class CLIOutputProvider : OutputProvider {
    override fun print(message: String) {
        println(message)
    }
}
