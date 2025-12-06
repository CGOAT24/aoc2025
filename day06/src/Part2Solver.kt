import kotlin.text.split

class Part2Solver: ProblemSolver() {
    override fun mapData(input: String): List<Problem> {
        var result = mutableListOf<Problem>()
        val lines = input.split("\n").filter { it.isNotEmpty() }
        var problemIndex = -1

        for (i in 0..lines[0].length) {
            val col = getColumn(lines, i) ?: continue
            if (col.second != null) {
                ++problemIndex
                if (problemIndex !in result.indices) {
                    result.add(Problem(
                        operator = Operator.UNDEFINED,
                        numbers = mutableListOf()
                    ))
                }
                result[problemIndex].operator = col.second!!
            }
            result[problemIndex].numbers.add(col.first)
        }
        return result
    }

    private fun getColumn(rows: List<String>, index: Int): Pair<ULong, Operator?>? {
        val col = rows.mapNotNull { it.getOrNull(index) }.map { it.toString().trim() }
        if (col.joinToString("").isEmpty()) {
            return null
        }

        val num = if (col.reversed()[0].toIntOrNull() != null) {
            col.joinToString("").toULong()
        } else {
            col.dropLast(1).joinToString("").toULong()
        }

        val op = Operator.fromString(col.reversed()[0])
        return num to (if (op == Operator.UNDEFINED) { null } else { op })
    }
}
