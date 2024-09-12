package linter

data class LinterConfig(
    val allow_expression_in_println: Boolean? = null,
    val allow_expression_in_readinput: Boolean? = null,
    val naming_convention: String? = null,
)
