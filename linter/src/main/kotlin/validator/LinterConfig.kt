package validator

data class LinterConfig(
    val allow_expression_in_println: Boolean,
    val naming_convention: String,
)
