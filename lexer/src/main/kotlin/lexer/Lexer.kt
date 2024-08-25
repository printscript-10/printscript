package lexer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import utils.Position
import utils.Token
import utils.TokenRegex
import utils.TokenType

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

                if (token.type != TokenType.WHITESPACE) {
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
                val matchedLenght = matchResult.value.length
                var matchedValue = matchResult.value

                if (tokenRegex.token == "STRING") {
                    matchedValue = matchedValue.substring(1, matchedValue.length - 1)
                }

                val token =
                    Token(
                        type = TokenType.valueOf(tokenRegex.token),
                        value = matchedValue,
                        position = Position(line, position, position + matchedLenght),
                    )
                return token
            }
        }
        error("Unexpected token at ($line:$position)")
    }

    private fun getTokenRegexes(): List<TokenRegex> {
        val resourceStream = this::class.java.classLoader.getResourceAsStream("tokens.json")
            ?: throw IllegalArgumentException("Resource not found: tokens.json")
        return mapper.readValue(resourceStream)
    }
}
