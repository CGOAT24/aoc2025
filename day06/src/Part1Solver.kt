class Part1Solver: ProblemSolver() {
    override fun mapData(input: String): List<Problem> {
        var result = mutableListOf<Problem>()
        val lines = input.split("\n")
        for (i in 0..<lines.size) {
            val regexp = Regex("\\d+(?:\\s+\\d+)*")
            if (lines[i].isEmpty()) {
                continue
            }

            if (lines[i].contains(regexp)) {
                val numbers = lines[i]
                    .split(" ")
                    .filter { it.trim().isNotEmpty() }
                    .map { it.toULong() }
                if (result.isEmpty()) {
                    result = MutableList(numbers.size) {
                        Problem(
                            operator = Operator.UNDEFINED,
                            numbers = mutableListOf()
                        )
                    }
                }
                for (j in 0..<numbers.size) {
                    result[j].numbers.add(numbers[j])
                }
            }
            else {
                val operators = lines[i]
                    .split(" ")
                    .filter { it.trim().isNotEmpty() }
                    .map { Operator.fromString(it) }
                for (j in 0..<operators.size) {
                    result[j].operator = operators[j]
                }
            }
        }
        return result
    }
}