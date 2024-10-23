package parser.nodeBuilder

import ast.BooleanLiteral
import ast.Identifier
import ast.NumberLiteral
import ast.StringLiteral
import ast.Type
import ast.VariableType
import token.Token

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
        val value = token.value.toBoolean()
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
