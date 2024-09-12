package linter

import lexer.linter.LinterFailure
import lexer.linter.LinterSuccess
import lexer.linter.LintingError
import linter.validator.ExpressionInPrintValidator
import linter.validator.ExpressionInReadInputValidator
import linter.validator.IfStatementValidator
import linter.validator.NamingConventionValidator
import linter.validator.Validator
import utils.AST
import utils.Result

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
        val baseList = listOf<Validator>(
            ExpressionInPrintValidator(config),
            NamingConventionValidator(config),
            ExpressionInReadInputValidator(config),
        )
        return when (version) {
            "1.0" -> baseList
            "1.1" -> {
                baseList.toMutableList().apply {
                    addAll(
                        listOf(
                            IfStatementValidator(config, version),
                        ),
                    )
                }
            }
            else -> throw IllegalArgumentException("Invalid version")
        }
    }
}
