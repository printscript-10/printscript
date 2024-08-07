package parser.astbuilder

import parser.BuildResult
import utils.Token

interface ASTBuilder {

    fun build(tokens: List<Token>, position: Int): BuildResult
}