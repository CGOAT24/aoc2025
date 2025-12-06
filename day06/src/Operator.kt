enum class Operator(val op: Char) {
    ADD('+'),
    MULTIPLY('*'),
    UNDEFINED('?');

    companion object {
        fun fromString(input: String): Operator {
            return entries.find { it.op.toString() == input.trim() } ?: UNDEFINED
        }

        fun fromChar(input: Char): Operator {
            return entries.find { it.op == input } ?: UNDEFINED
        }
    }
}