package formatter

data class FormatterConfig(
    val declaration_colon_trailing_whitespaces: Boolean = false,
    val declaration_colon_leading_whitespaces: Boolean = false,
    val assignation_equal_wrap_whitespaces: Boolean = false,
    val println_trailing_line_jump: Int = 0,
    val if_block_indent_spaces: Int = 1,
)
