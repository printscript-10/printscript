package linter

import lexer.linter.LinterFailure
import lexer.linter.LinterSuccess
import lexer.linter.LintingError
import linter.validator.ExpressionInPrintValidator
import linter.validator.NamingConventionValidator
import linter.validator.Validator
import utils.AST
import utils.Result

class Linter(config: LinterConfig) {

    private val validators = listOf<Validator>(
        ExpressionInPrintValidator(config),
        NamingConventionValidator(config),
    )

    fun execute(ast: AST): Result {
        val errors: MutableList<LintingError> = mutableListOf()
        for (validator in validators) {
            val validationError = validator.validate(ast)
            if (validationError != null) errors.add(validationError)
        }
        if (errors.isEmpty()) return LinterSuccess
        return LinterFailure(errors.joinToString("\n") { it.message })
    }
}
