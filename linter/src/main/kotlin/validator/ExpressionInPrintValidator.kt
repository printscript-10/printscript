package validator

import lexer.LintingError
import utils.AST
import utils.Literal
import utils.PrintFunction

class ExpressionInPrintValidator : Validator {
    override fun validate(ast: AST): LintingError? {
        if (ast is PrintFunction) {
            if (ast.value !is Literal) {
                return LintingError("Print functions cannot contain expressions", ast.position)
            }
        }
        return null
    }
}
