package linter.validator

import ast.AST
import linter.LintingError

interface Validator {
    fun validate(ast: AST): LintingError?
}
