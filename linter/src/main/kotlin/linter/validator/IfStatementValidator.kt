package linter.validator

import lexer.linter.LintingError
import linter.Linter
import utils.AST
import utils.IfStatement

class IfStatementValidator: Validator {
    override fun validate(ast: AST): LintingError? {
        if(ast !is IfStatement) return null
        for(thenStatement in ast.thenStatements){
            val result = Linter()
        }
    }
}
