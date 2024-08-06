package utils

data class Token (
    val type: String,
    val value: String,
    val position: Position
)

data class TokenRegex (
    val token: String,
    val regex: String,
)