import lexer.LinterFailure
import lexer.LinterResult
import lexer.LinterSuccess
import lexer.LintingError
import utils.AST
import validator.ExpressionInPrintValidator
import validator.LinterConfig
import validator.NamingConventionValidator
import validator.Validator

class Linter(private val config: LinterConfig) {

    fun execute(ast: AST): LinterResult {
        val validators = getValidators()
        var errors: MutableList<LintingError> = ArrayList<LintingError>()
        for (validator in validators) {
            val validationError = validator.validate(ast)
            if (validationError != null) errors.add(validationError)
        }
        if (errors.isEmpty()) return LinterSuccess()
        return LinterFailure(errors)
    }

    private fun getValidators(): List<Validator> {
        var result = ArrayList<Validator>()
        if (!config.allow_expression_in_println) {
            result.add(ExpressionInPrintValidator())
        }
        result.add(NamingConventionValidator(config.naming_convention))
        return result
    }
}
