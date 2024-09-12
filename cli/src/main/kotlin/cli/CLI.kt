package cli

import formatter.FormatterConfig
import linter.LinterConfig
import org.yaml.snakeyaml.Yaml
import runner.Runner
import java.io.File
import java.io.InputStream
import kotlin.system.exitProcess

const val filePath = "cli/src/main/resources/test.txt"
const val configPath = "cli/src/main/resources/config.yml"

private val errorHandler = CLIErrorHandler()
private val outputProvider = CLIOutputProvider()
private val inputProvider = CLIInputProvider()
private val envProvider = CLIEnvProvider()

fun main() {
    val file = File(filePath)
    val configFile = File(configPath)
    val version = getVersion()
    val runner = Runner(version)
    while (true) {
        printGreen(appCli(version))
        printMenu()
        val number = readlnOrNull() ?: throw IllegalArgumentException("Choose a valid option!")
        when (number) {
            "1" -> validate(file, runner)
            "2" -> execute(file, runner)
            "3" -> format(file, configFile, runner)
            "4" -> analyze(file, configFile, runner)
            "5" -> exitProcess(0)
            else -> invalidOption(version)
        }
    }
}

private fun getVersion(): String {
    println("Input version (1.0, 1.1)")
    while (true) {
        val answer = readlnOrNull() ?: throw IllegalArgumentException("Choose a valid version!")
        if (answer == "1.0" || answer == "1.1") return answer
        println("Choose a valid option!")
    }
}

private fun validate(file: File, runner: Runner) {
    runner.validate(file.inputStream(), errorHandler)
    handleResult("Validated")
}

private fun execute(file: File, runner: Runner) {
    runner.execute(file.inputStream(), outputProvider, errorHandler, inputProvider, envProvider)
    handleResult("Executed")
}

private fun format(file: File, configFile: File, runner: Runner) {
    val formatterConfig = loadFormatConfig(configFile.inputStream())
    val newSnippet = runner.format(file.inputStream(), errorHandler, formatterConfig)
    if (newSnippet != null) file.writeText(newSnippet)
    handleResult("Formatted")
}

private fun analyze(file: File, configFile: File, runner: Runner) {
    val linterConfig = loadLinterConfig(configFile.inputStream())
    runner.analyze(file.inputStream(), errorHandler, linterConfig)
    handleResult("Analyzed")
}

private fun invalidOption(version: String) {
    clearConsole()
    println("Choose a valid option!")
    appCli(version)
}

private fun handleResult(command: String) {
    if (errorHandler.errorMessage.isNullOrBlank()) {
        printGreen("✓ File $command successfully")
    } else {
        printlnRed(errorHandler.errorMessage.toString())
        errorHandler.errorMessage = null
    }
    exitCli()
}

private fun loadLinterConfig(input: InputStream): LinterConfig {
    val yaml = Yaml()
    val configMap: Map<String, Any> = yaml.load(input)

    val linterConfig = (configMap["linter"] as Map<*, *>)["rules"] as Map<*, *>

    return LinterConfig(
        allow_expression_in_println = linterConfig["allow_expression_in_println"] as Boolean? ?: true,
        naming_convention = linterConfig["naming_convention"] as String? ?: "camel_case",
    )
}

private fun loadFormatConfig(input: InputStream): FormatterConfig {
    val yaml = Yaml()
    val configMap: Map<String, Any> = yaml.load(input)

    val formatterConfig = (configMap["formatter"] as Map<*, *>)["rules"] as Map<*, *>

    return FormatterConfig(
        declaration_colon_trailing_whitespaces =
            formatterConfig["declaration_colon_trailing_whitespaces"] as Boolean? ?: false,
        declaration_colon_leading_whitespaces =
            formatterConfig["declaration_colon_leading_whitespaces"] as Boolean? ?: false,
        assignation_equal_wrap_whitespaces =
            formatterConfig["assignation_equal_wrap_whitespaces"] as Boolean? ?: false,
        println_trailing_line_jump =
            formatterConfig["println_trailing_line_jump"] as Int? ?: 0,
    )
}

private fun clearConsole() {
    repeat(100) {
        println()
    }
}

private fun appCli(version: String): String {
    return "\n" +
        " /\$\$\$\$\$\$\$           /\$\$             /\$\$         " +
        "                         /\$\$             /\$\$   ™ \n" +
        "| \$\$__  \$\$         |__/            | \$\$                " +
        "                 |__/            | \$\$    \n" +
        "| \$\$  \\ \$\$ /\$\$\$\$\$\$  /\$\$ /\$\$\$\$\$\$\$  /\$\$\$\$" +
        "\$\$   /\$\$\$\$\$\$\$  /\$\$\$\$\$\$\$  /\$\$\$\$\$\$  /\$\$  /\$" +
        "\$\$\$\$\$  /\$\$\$\$\$\$  \n" +
        "| \$\$\$\$\$\$\$//\$\$__  \$\$| \$\$| \$\$__  \$\$|_  \$\$_/  /\$" +
        "\$_____/ /\$\$_____/ /\$\$__  \$\$| \$\$ /\$\$__  \$\$|_  \$\$_/  \n" +
        "| \$\$____/| \$\$  \\__/| \$\$| \$\$  \\ \$\$  | \$\$   |  \$\$\$\$" +
        "\$\$ | \$\$      | \$\$  \\__/| \$\$| \$\$  \\ \$\$  | \$\$    \n" +
        "| \$\$     | \$\$      | \$\$| \$\$  | \$\$  | \$\$ /\$\$\\____  \$" +
        "\$| \$\$      | \$\$      | \$\$| \$\$  | \$\$  | \$\$ /\$\$\n" +
        "| \$\$     | \$\$      | \$\$| \$\$  | \$\$  |  \$\$\$\$//\$\$\$\$" +
        "\$\$\$/|  \$\$\$\$\$\$\$| \$\$      | \$\$| \$\$\$\$\$\$\$/  |  \$\$\$\$/\n" +
        "|__/     |__/      |__/|__/  |__/   \\___/ |_______/  \\_______/|__/" +
        "      |__/| \$\$____/    \\___/  \n" +
        "                                                                            | \$\$                \n" +
        "                                                                            | \$\$              $version  \n" +
        "                                                                            |__/                \n"
}

private fun printMenu() {
    println(
        "Que onda que hacemos machine\n" +
            "   1) Validation \n" +
            "   2) Execution \n" +
            "   3) Formatting \n" +
            "   4) Analyzing \n" +
            "   5) Exit CLI",
    )
}

private fun exitCli() {
    println("Exit CLI (y/n)")
    while (true) {
        val answer = readlnOrNull() ?: throw IllegalArgumentException("Choose a valid option!")
        if (answer == "y") exitProcess(0)
        if (answer == "n") {
            clearConsole()
            return
        }
        println("Choose a valid option!")
    }
}

private fun printlnRed(message: String) {
    println("\u001B[31m${message}\u001B[0m")
}

private fun printGreen(message: String) {
    println("\u001B[32m${message}\u001B[0m")
}
