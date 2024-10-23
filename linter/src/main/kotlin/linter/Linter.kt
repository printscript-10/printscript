package linter

import ast.AST
import linter.validator.ExpressionInPrintValidator
import linter.validator.ExpressionInReadInputValidator
import linter.validator.IfStatementValidator
import linter.validator.NamingConventionValidator
import linter.validator.Validator
import result.Result

class Linter(config: LinterConfig, version: String) {

    private val validators: List<Validator>

    init {
        validators = getValidators(config, version)
    }

    fun execute(ast: AST): Result {
        val errors: MutableList<LintingError> = mutableListOf()
        for (validator in validators) {
            val validationError = validator.validate(ast)
            if (validationError != null) errors.add(validationError)
        }
        if (errors.isEmpty()) return LinterSuccess
        return LinterFailure(errors.joinToString("\n") { it.message })
    }

    private fun getValidators(config: LinterConfig, version: String): List<Validator> {
        val baseList: List<Validator> = listOfNotNull(
            config.naming_convention?.let {
                NamingConventionValidator(config)
            },
            config.allow_expression_in_println?.let {
                ExpressionInPrintValidator(config)
            },
        )

        return when (version) {
            "1.0" -> baseList
            "1.1" -> {
                baseList.toMutableList().apply {
                    config.allow_expression_in_readinput?.let {
                        add(ExpressionInReadInputValidator(config))
                    }
                    add(IfStatementValidator(config, version))
                }
            }

            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
