package validator

import lexer.LintingError
import utils.AST

interface Validator {
    fun validate(ast: AST): LintingError?
}
