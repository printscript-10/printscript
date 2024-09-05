package lexer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import utils.Position
import utils.Result
import utils.Token
import utils.TokenRegex
import utils.TokenType

class Lexer {

    private val mapper: ObjectMapper = jacksonObjectMapper()
    private val tokenRegexes: List<TokenRegex>

    init {
        this.tokenRegexes = getTokenRegexes()
    }

    fun tokenize(input: String, line: Int): Result {
        val tokens = mutableListOf<Token>()
        var currentPosition = 0

        while (currentPosition < input.length) {
            val currentLine = input.substring(currentPosition)
            val token: Token = getToken(line, currentPosition, currentLine) ?: return LexingFailure(
                "Unexpected token at ($currentLine:$currentPosition)",
            )
            if (token.type != TokenType.WHITESPACE) {
                tokens.add(token)
            }

            currentPosition = token.position.end
        }
        return LexingSuccess(tokens)
    }

    private fun getToken(line: Int, position: Int, currentString: String): Token? {
        for (tokenRegex in tokenRegexes) {
            val matchResult = tokenRegex.regex.toRegex().find(currentString)

            if (matchResult != null && matchResult.range.first == 0) {
                val matchedLength = matchResult.value.length
                var matchedValue = matchResult.value

                if (tokenRegex.token == "STRING") {
                    matchedValue = matchedValue.substring(1, matchedValue.length - 1)
                }

                val token =
                    Token(
                        type = TokenType.valueOf(tokenRegex.token),
                        value = matchedValue,
                        position = Position(line, position, position + matchedLength),
                    )
                return token
            }
        }
        return null
    }

    private fun getTokenRegexes(): List<TokenRegex> {
        val resourceStream = this::class.java.classLoader.getResourceAsStream("tokens.json")!!
        return mapper.readValue(resourceStream)
    }
}
