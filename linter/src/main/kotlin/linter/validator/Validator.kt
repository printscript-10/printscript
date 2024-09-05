package linter.validator

import lexer.linter.LintingError
import utils.AST

interface Validator {
    fun validate(ast: AST): LintingError?
}
