package cli

import runner.Runner
import java.io.File

const val filePath = "cli/src/main/resources/test.txt"

fun main() {
    val runner = Runner()
    val errorHandler = CLIErrorHandler()
    val version = "1.0"
    while (true) {
        printGreen(appCli(version))
        printMenu()
        val number = readlnOrNull() ?: throw IllegalArgumentException("Choose a valid option")
        when (number) {
            "1" -> {
                val file = File(filePath)
                runner.run(file.inputStream(), errorHandler)
                if (errorHandler.errorMessage.isNullOrBlank()) {
                    println("\u001B[32m✓ File Ran successfully\u001B[0m")
                } else {
                    printlnRed(errorHandler.errorMessage.toString())
                    errorHandler.errorMessage = null
                }
                println("Exit CLI? (y)")
                val answer = readlnOrNull() ?: throw IllegalArgumentException("Choose a valid option")
                if (answer == "y") {
                    System.exit(0)
                }
                clearConsole()
            }
            "2" -> System.exit(0)
            else -> {
                clearConsole()
                println("Choose a valid option!!!")
                appCli(version)
            }
        }
    }
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
            "   1) Correr programinha \n" +
            "   2) Salir",
    )
}

private fun printlnRed(message: String) {
    println("\u001B[31m${message}\u001B[0m")
}

private fun printGreen(message: String) {
    println("\u001B[32m${message}\u001B[0m")
}
