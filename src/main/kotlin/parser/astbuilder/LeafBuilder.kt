package parser.astbuilder

import parser.BuildResult
import parser.Success
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral
import utils.Token

class StringLiteralBuilder: ASTBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val token = tokens[position]
        return Success(
            result = StringLiteral(
                value = token.value,
                position = token.position
            ),
            position = position
        )
    }
}

class NumericLiteralBuilder: ASTBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val token = tokens[position]
        val num: Number = if(token.value.contains('.')){token.value.toDouble()} else {token.value.toInt()}
        return Success(
            result = NumberLiteral(
                value = num,
                position = token.position
            ),
            position = position
        )
    }
}

class IdentifierBuilder: ASTBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        val token = tokens[position]
        return Success(
            result = Identifier(
                name = token.value,
                position = token.position
            ),
            position = position
        )
    }
}