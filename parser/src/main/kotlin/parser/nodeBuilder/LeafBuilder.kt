package parser.nodeBuilder

import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral
import utils.Token
import utils.Type

class StringLiteralBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildSuccess {
        val token = tokens[position]
        return BuildSuccess(
            result = StringLiteral(
                value = token.value,
                position = token.position,
            ),
            position = position,
        )
    }
}

class NumericLiteralBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildSuccess {
        val token = tokens[position]
        val num: Number = if (token.value.contains('.')) { token.value.toDouble() } else { token.value.toInt() }
        return BuildSuccess(
            result = NumberLiteral(
                value = num,
                position = token.position,
            ),
            position = position,
        )
    }
}

class IdentifierBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildSuccess {
        val token = tokens[position]
        return BuildSuccess(
            result = Identifier(
                name = token.value,
                position = token.position,
            ),
            position = position,
        )
    }
}

class TypeBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildSuccess {
        val token = tokens[position]
        return BuildSuccess(
            result = Type(
                name = token.value,
                position = token.position,
            ),
            position = position,
        )
    }
}
