package cli

import provider.OutputProvider

class CLIOutputProvider : OutputProvider {
    override fun print(message: String) {
        println(message)
    }
}
