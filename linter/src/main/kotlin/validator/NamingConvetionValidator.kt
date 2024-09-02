package validator

import lexer.LintingError
import utils.AST
import utils.VariableDeclaration

class NamingConvetionValidator(private val namingConvetion: String): Validator {
    override fun validate(ast: AST): LintingError? {
        if(ast is VariableDeclaration){
            if(!checkNamingConvention(ast.id.name)){
                return LintingError("Identifier does not match ${namingConvetion}", position = ast.position)
            }
        }
        return null
    }

    private fun checkNamingConvention(value: String): Boolean {
        return when (namingConvetion) {
            // TODO; cambiar esto por enum asi no esta el else ese feo
            "camel_case" -> {
                val camelCaseRegex = Regex("\\b[a-z]+(?:[A-Z][a-z]*)*\\b")
                camelCaseRegex.matches(value)
            }
            "snake_case" -> {
                val snakeCaseRegex = Regex("\\b[a-z]+(?:_[a-z]+)*\\b")
                snakeCaseRegex.matches(value)
            }
            else -> {
                false
            }
        }
    }
}
