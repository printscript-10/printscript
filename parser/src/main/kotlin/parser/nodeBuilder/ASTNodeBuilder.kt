package parser.nodeBuilder

import result.Result
import token.Token

interface ASTNodeBuilder {
    fun build(tokens: List<Token>, position: Int): Result
}
