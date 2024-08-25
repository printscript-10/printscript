package parser.nodeBuilder

import utils.Token

interface ASTNodeBuilder {

    fun build(tokens: List<Token>, position: Int): BuildResult
}
