package linter.validator

import ast.AST
import ast.IfStatement
import linter.Linter
import linter.LinterConfig
import linter.LintingError
import result.Failure

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
