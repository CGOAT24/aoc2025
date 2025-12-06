import java.io.File
import java.io.InputStream

fun readFile(filepath: String): String {
    val inputStream: InputStream = File(filepath).inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun main() {
    val input = readFile("input.txt")
    val part1 = Part1Solver()
    val part2 = Part2Solver()

    println("Part 1: " + part1.solve(input))
    println("Part 2: " + part2.solve(input))
}