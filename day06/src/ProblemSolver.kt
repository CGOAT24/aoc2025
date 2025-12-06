abstract class ProblemSolver {
    protected abstract fun mapData(input: String): List<Problem>

    protected fun calculate(problem: Problem): ULong {
        return when(problem.operator) {
            Operator.ADD -> { problem.numbers.sumOf { it } }
            Operator.MULTIPLY -> { problem.numbers.reduce { acc, i -> acc * i } }
            else -> { 0 }
        } as ULong
    }

    protected fun aggregateResults(problems: List<Problem>): ULong {
        return problems.sumOf { calculate(it) }
    }

    fun solve(input: String): ULong {
        return aggregateResults(mapData(input))
    }
}