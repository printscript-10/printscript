package utils

enum class BinaryOperators(val symbol: String) {
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/");

    companion object {
        private val map = entries.associateBy(BinaryOperators::symbol)

        fun fromSymbol(symbol: String): BinaryOperators? {
            return map[symbol]
        }
    }
}
