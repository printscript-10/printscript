package parser.nodeBuilder

import utils.BooleanLiteral
import utils.Identifier
import utils.NumberLiteral
import utils.StringLiteral
import utils.Token
import utils.Type
import utils.VariableType

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

class BooleanLiteralBuilder : ASTNodeBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildSuccess {
        val token = tokens[position]
        val value = when (token.value) {
            "true" -> true
            "false" -> false
            else -> throw Error("Unexpected boolean token value:  ${token.value}")
        }
        return BuildSuccess(
            result = BooleanLiteral(
                value = value,
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
                name = VariableType.valueOf(token.value.uppercase()),
                position = token.position,
            ),
            position = position,
        )
    }
}
