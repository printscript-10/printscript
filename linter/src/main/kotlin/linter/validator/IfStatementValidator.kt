package linter.validator

import lexer.linter.LintingError
import linter.Linter
import linter.LinterConfig
import utils.AST
import utils.Failure
import utils.IfStatement

class IfStatementValidator(private val config: LinterConfig, private val version: String) : Validator {
    override fun validate(ast: AST): LintingError? {
        val errors = mutableListOf<Failure>()
        val linter = Linter(config, version)
        if (ast !is IfStatement) return null
        for (thenStatement in ast.thenStatements) {
            val result = linter.execute(thenStatement)
            if (result is Failure) errors.add(result)
        }
        for (elseStatement in ast.elseStatements) {
            val result = linter.execute(elseStatement)
            if (result is Failure) errors.add(result)
        }
        if (errors.isEmpty()) return null
        return LintingError(errors.joinToString("\n") { it.error })
    }
}
