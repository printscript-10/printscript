package semanticAnalizerTests

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.semanticAnalizer.Failure
import parser.semanticAnalizer.SemanticAnalyzer
import parser.semanticAnalizer.Success
import utils.Identifier
import utils.IfStatement
import utils.Position
import utils.StringLiteral
import utils.Type
import utils.VariableDeclaration
import utils.VariableType
import kotlin.test.assertEquals

class IfStatementTest {
    @Test
    fun `test validIfElseStatementReturnsSuccess`() {
        val dummyPosition = Position(0, 0, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                dummyPosition,
            ),
        )
        val expectedElseStatement = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
                dummyPosition,
            ),
        )
        val ifStatement = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val variables: Map<String, VariableType> = mapOf("a" to VariableType.BOOLEAN)
        val semanticAnalyzer = SemanticAnalyzer(variables)

        val result = semanticAnalyzer.analyze(ifStatement)

        assertEquals(result, Success(variables))
    }

    @Test
    fun `test validIfElseStatementDoenstModifyContext`() {
        val dummyPosition = Position(0, 0, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                dummyPosition,
            ),
        )
        val expectedElseStatement = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
                dummyPosition,
            ),
        )
        val ifStatement = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val variables: Map<String, VariableType> = mapOf(
            "a" to VariableType.BOOLEAN,
            "outerVariable" to VariableType.STRING,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)

        val result = semanticAnalyzer.analyze(ifStatement)

        assertEquals(result, Success(variables))
    }

    @Test
    fun `test invalidIfElseStatementReturnsFailure`() {
        val dummyPosition = Position(0, 0, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                dummyPosition,
            ),
        )
        val expectedElseStatement = listOf(
            VariableDeclaration(
                Identifier(name = "b", dummyPosition),
                Type(name = VariableType.NUMBER, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
                dummyPosition,
            ),
        )
        val ifStatement = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val variables: Map<String, VariableType> = mapOf()
        val semanticAnalyzer = SemanticAnalyzer(variables)

        val result = semanticAnalyzer.analyze(ifStatement)

        assertTrue(result is Failure)
    }

    @Test
    fun `test declaredVariableReturnsError`() {
        val dummyPosition = Position(0, 0, 0)
        val expectedThenStatemets = listOf(
            VariableDeclaration(
                Identifier(name = "declaredVariable", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "b", dummyPosition),
                dummyPosition,
            ),
        )
        val expectedElseStatement = listOf(
            VariableDeclaration(
                Identifier(name = "declaredVariable", dummyPosition),
                Type(name = VariableType.STRING, dummyPosition),
                StringLiteral(value = "c", dummyPosition),
                dummyPosition,
            ),
        )
        val ifStatement = IfStatement(
            condition = Identifier("a", dummyPosition),
            thenStatements = expectedThenStatemets,
            elseStatements = expectedElseStatement,
            position = dummyPosition,
        )
        val variables: Map<String, VariableType> = mapOf(
            "declaredVariable" to VariableType.STRING,
        )
        val semanticAnalyzer = SemanticAnalyzer(variables)

        val result = semanticAnalyzer.analyze(ifStatement)

        assertTrue(result is Failure)
    }
}
