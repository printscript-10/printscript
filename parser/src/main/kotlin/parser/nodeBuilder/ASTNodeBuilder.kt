package parser.nodeBuilder

import utils.Result
import utils.Token

interface ASTNodeBuilder {
    fun build(tokens: List<Token>, position: Int): Result
}
