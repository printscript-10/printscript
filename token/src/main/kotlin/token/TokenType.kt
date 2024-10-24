package token

enum class TokenType {
    WHITESPACE,
    STRING,
    VARIABLE_DECLARATOR,
    BOOLEAN,
    TYPE,
    PRINT,
    READ_INPUT,
    READ_ENV,
    IDENTIFIER,
    NUMBER,
    COLON,
    SEMICOLON,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_BRACE,
    CLOSE_BRACE,
    BINARY_OPERATOR,
    ASSIGN,
    IF,
    ELSE,
}
