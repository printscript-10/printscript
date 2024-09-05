package cli

import utils.ErrorHandler

class CLIErrorHandler : ErrorHandler {
    var errorMessage: String? = null

    override fun reportError(message: String) {
        errorMessage = message
    }
}
