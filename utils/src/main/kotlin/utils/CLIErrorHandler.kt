package utils

class CLIErrorHandler: ErrorHandler {
    override fun reportError(message: String) {
        println("\r\u001B[31m${message}\u001B[0m")
    }
}
