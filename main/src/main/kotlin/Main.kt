import runner.Runner
import utils.CLIErrorHandler
import java.io.File

val filePath = "main/src/main/resources/test.txt"

fun main() {
    val file = File(filePath)
    val runner = Runner()
    val errorHandler = CLIErrorHandler()
    runner.run(file.inputStream(), errorHandler)
}
