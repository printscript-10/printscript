package linter

data class LinterConfig(
    val allow_expression_in_println: Boolean = true,
    val allow_expression_in_readinput: Boolean = true,
    val naming_convention: String = "camel_case",
)
