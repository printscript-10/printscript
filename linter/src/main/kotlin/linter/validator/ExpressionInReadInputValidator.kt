package linter.validator

import lexer.linter.LintingError
import linter.LinterConfig
import utils.AST
import utils.Expression
import utils.Identifier
import utils.Literal
import utils.PrintFunction
import utils.ReadInput
import utils.VariableAssignation
import utils.VariableDeclaration

class ExpressionInReadInputValidator(private val config: LinterConfig) : Validator {
    override fun validate(ast: AST): LintingError? {
        var value: Expression? = null
        if (ast is VariableDeclaration) {
            if (ast.init != null) {
                value = ast.init!!
            } else {
                return null
            }
        } else if (ast is VariableAssignation) {
            value = ast.value
        } else if (ast is PrintFunction) value = ast.value
        if (value == null) return null
        if (value is ReadInput && !config.allow_expression_in_readinput) {
            if (value.message !is Literal && value.message !is Identifier) {
                return LintingError("ReadInput functions cannot contain expressions")
            }
        }
        return null
    }
}
