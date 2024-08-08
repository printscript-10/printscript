package parser.nodeBuilder

import parser.BuildResult
import utils.Token

interface ASTNodeBuilder {

    fun build(tokens: List<Token>, position: Int): BuildResult
}