package lexer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import utils.Position
import utils.Token
import utils.TokenRegex
import utils.TokenType
import java.io.File

class Lexer {

    private val mapper: ObjectMapper = jacksonObjectMapper()
    private var tokenRegexes: List<TokenRegex>

    init {
        this.tokenRegexes = getTokenRegexes()
    }

    fun tokenize(input: String): List<Token> {
        val tokens = mutableListOf<Token>()
        for ((index, line) in input.lines().withIndex()) {
            var currentPosition = 0

            while (currentPosition < line.length) {
                val currentLine = line.substring(currentPosition)
                val token: Token = getToken(index, currentPosition, currentLine)

                if(token.type != TokenType.WHITESPACE){
                    tokens.add(token)
                }

                currentPosition = token.position.end
            }
        }
        return tokens
    }

    private fun getToken(line: Int, position: Int, currentString: String): Token {
        for (tokenRegex in tokenRegexes) {
            val matchResult = tokenRegex.regex.toRegex().find(currentString)

            if (matchResult != null && matchResult.range.first == 0) {
                val matchedValue = matchResult.value

                val token = Token(type= TokenType.valueOf(tokenRegex.token) , value= matchedValue, position= Position(line, position, position + matchedValue.length))
                return token
            }
        }
        error("Unexpected token at ($line:$position)")
    }

    private fun getTokenRegexes(): List<TokenRegex> {
        return mapper.readValue(File("resources/tokens.json"))
    }
}