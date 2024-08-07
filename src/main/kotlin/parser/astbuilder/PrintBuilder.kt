package parser.astbuilder

import parser.BuildResult
import parser.Failure
import utils.Token
import utils.TokenType

class PrintBuilder: ASTBuilder {

    override fun build(tokens: List<Token>, position: Int): BuildResult {
        if(tokens[position + 1].type != TokenType.OPEN_BRACE){
            return Failure(
                error = "Expected opening brace at line ${tokens[position + 1].position.line}",
                position = position
            )
        }


    }

}