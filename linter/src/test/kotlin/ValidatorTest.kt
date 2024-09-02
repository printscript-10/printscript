import lexer.LintingError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import utils.Identifier
import utils.Position
import utils.Type
import utils.VariableDeclaration
import utils.VariableType
import validator.NamingConventionValidator

class ValidatorTest {

    @Test
    fun `test validCamelCaseReturnsNull`(){
        val namingConvention = "camel_case"
        val namingConventionValidator = NamingConventionValidator(namingConvention)
        val dummyPosition = Position(0,0,0)
        val identifier = Identifier("camelCase", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, dummyPosition)

        val result = namingConventionValidator.validate(variableDeclaration)
        val expectedResult = null

        assertEquals(result, expectedResult)
    }

    @Test
    fun `test invalidCamelCaseReturnsError`(){
        val namingConvention = "camel_case"
        val namingConventionValidator = NamingConventionValidator(namingConvention)
        val dummyPosition = Position(0,0,0)
        val identifier = Identifier("camel_case", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, dummyPosition)

        val result = namingConventionValidator.validate(variableDeclaration)
        // usar is en vez de comparacion con mensaje de error?
        assertTrue(result is LintingError)
    }

    @Test
    fun `test validSnakeCaseReturnsNull`(){
        val namingConvention = "snake_case"
        val namingConventionValidator = NamingConventionValidator(namingConvention)
        val dummyPosition = Position(0,0,0)
        val identifier = Identifier("snake_case_identifier", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, dummyPosition)

        val result = namingConventionValidator.validate(variableDeclaration)
        val expectedResult = null

        assertEquals(result, expectedResult)
    }

    @Test
    fun `test invalidSnakeCaseReturnsError`(){
        val namingConvention = "snake_case"
        val namingConventionValidator = NamingConventionValidator(namingConvention)
        val dummyPosition = Position(0,0,0)
        val identifier = Identifier("snakeCase", dummyPosition)
        val type = Type(VariableType.STRING, dummyPosition)
        val variableDeclaration = VariableDeclaration(identifier, type, init = null, dummyPosition)

        val result = namingConventionValidator.validate(variableDeclaration)

        assertTrue(result is LintingError)
    }
}
