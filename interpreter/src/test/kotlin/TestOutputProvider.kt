import utils.OutputProvider

class TestOutputProvider : OutputProvider {
    var output: Any? = null

    override fun print(message: Any?) {
        output = message
    }
}
