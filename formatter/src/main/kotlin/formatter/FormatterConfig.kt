package formatter

data class FormatterConfig(
    val declaration_colon_trailing_whitespaces: Boolean?,
    val declaration_colon_leading_whitespaces: Boolean?,
    val assignation_equal_wrap_whitespaces: Boolean?,
    val println_trailing_line_jump: Int?,
    val if_block_indent_spaces: Int = 4,
    val base_indent_level: Int = 1,
)
