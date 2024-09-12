package linter.validator

import lexer.linter.LintingError
import linter.LinterConfig
import utils.AST
import utils.VariableDeclaration

class NamingConventionValidator(private val config: LinterConfig) : Validator {
    override fun validate(ast: AST): LintingError? {
        if (config.naming_convention == null) return null
        if (ast is VariableDeclaration) {
            val namingConvention = config.naming_convention
            if (namingConvention != "camel_case" && namingConvention != "snake_case") {
                return LintingError("Invalid naming convention")
            }

            if (!checkNamingConvention(ast.id.name, namingConvention)) {
                return LintingError("Identifier ${ast.id.name} does not match $namingConvention")
            }
        }
        return null
    }

    private fun checkNamingConvention(value: String, namingConvention: String): Boolean {
        return when (namingConvention) {
            "camel_case" -> {
                val camelCaseRegex = Regex("\\b[a-z]+(?:[A-Z][a-z]*)*\\b")
                camelCaseRegex.matches(value)
            }
            "snake_case" -> {
                val snakeCaseRegex = Regex("\\b[a-z]+(?:_[a-z]+)*\\b")
                snakeCaseRegex.matches(value)
            }
            else -> false
        }
    }
}
