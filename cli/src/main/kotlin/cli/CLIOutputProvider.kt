package cli

import utils.OutputProvider

class CLIOutputProvider : OutputProvider {
    override fun print(message: Any?) {
        println(message)
    }
}
