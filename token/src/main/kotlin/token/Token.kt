package token

import position.Position

data class Token(
    val type: TokenType,
    val value: String,
    val position: Position,
)

data class TokenRegex(
    val token: String,
    val regex: String,
)
