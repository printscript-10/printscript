package linter.validator

import lexer.linter.LintingError
import linter.LinterConfig
import utils.AST
import utils.Identifier
import utils.Literal
import utils.PrintFunction

class ExpressionInPrintValidator(private val config: LinterConfig) : Validator {
    override fun validate(ast: AST): LintingError? {
        if (config.allow_expression_in_println == null) return null
        if (ast is PrintFunction && !config.allow_expression_in_println) {
            if (ast.value !is Literal && ast.value !is Identifier) {
                return LintingError("Print functions cannot contain expressions")
            }
        }
        return null
    }
}
