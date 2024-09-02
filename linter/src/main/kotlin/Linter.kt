import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import lexer.LinterFailure
import lexer.LinterResult
import lexer.LinterSuccess
import lexer.LintingError
import utils.AST
import validator.ExpressionInPrintValidator
import validator.NamingConventionValidator
import validator.Validator
import java.io.File

val configPath = "src/main/resources/linter.config.yml"

data class Config(
    val allow_expression_in_println: Boolean,
    val naming_convention: String,
)

class Linter {

    private var config: Config

    init {
        config = loadConfig()
    }
    fun execute(ast: AST): LinterResult {
        val validators = getValidators()
        var errors: MutableList<LintingError> = ArrayList<LintingError>()
        for (validator in validators) {
            val validationError = validator.validate(ast)
            if (validationError != null) errors.add(validationError)
        }
        if (errors.isEmpty()) return LinterSuccess()
        return LinterFailure(errors)
    }

    private fun getValidators(): List<Validator> {
        var result = ArrayList<Validator>()
        if (!config.allow_expression_in_println) {
            result.add(ExpressionInPrintValidator())
        }
        result.add(NamingConventionValidator(config.naming_convention))
        return result
    }

    private fun loadConfig(): Config {
        val file = File(configPath)
        val yamlConfig = file.readText()

        val mapper = YAMLMapper().registerKotlinModule()
        val config: Config = mapper.readValue(yamlConfig, Config::class.java)
        return config
    }
}
