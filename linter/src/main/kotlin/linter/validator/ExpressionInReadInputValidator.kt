package linter.validator

import ast.AST
import ast.Expression
import ast.Identifier
import ast.Literal
import ast.PrintFunction
import ast.ReadInput
import ast.VariableAssignation
import ast.VariableDeclaration
import linter.LinterConfig
import linter.LintingError

class ExpressionInReadInputValidator(private val config: LinterConfig) : Validator {
    override fun validate(ast: AST): LintingError? {
        if (config.allow_expression_in_readinput == null) return null
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
