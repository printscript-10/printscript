package org.example.utils

data class Token (
    val type: String,
    val value: String,
    val position: Position
)

data class Position (
    val line: Int,
    val start: Int,
    val end: Int,
)

data class TokenRegex (
    val token: String,
    val regex: String,
)