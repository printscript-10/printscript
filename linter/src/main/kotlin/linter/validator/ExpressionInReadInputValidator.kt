package linter.validator

import lexer.linter.LintingError
import linter.LinterConfig
import utils.AST
import utils.Identifier
import utils.Literal
import utils.ReadInput

class ExpressionInReadInputValidator(private val config: LinterConfig) : Validator {
    override fun validate(ast: AST): LintingError? {
        if (ast is ReadInput && !config.allow_expression_in_readinput) {
            if (ast.message !is Literal && ast.message !is Identifier) {
                return LintingError("ReadInput functions cannot contain expressions")
            }
        }
        return null
    }
}
