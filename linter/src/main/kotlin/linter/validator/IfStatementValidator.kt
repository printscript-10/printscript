package linter.validator

import lexer.linter.LintingError
import utils.AST
import utils.IfStatement

class IfStatementValidator : Validator {
    override fun validate(ast: AST): LintingError? {
        if (ast !is IfStatement) return null
        return null
    }
}
