import java.io.File

val sample = """

""".trimMargin()


fun main() {
	testSamplePart1()
	testSamplePart2()
	val result1 = solvePart1()
	println("Answer part1: $result1")
	val result2 = solvePart2()
	println("Answer part2: $result2")
}

private fun solvePart1(): Int {
	return 0
}

private fun solvePart2(): Int {
	return 0
}

private fun readInput(fileName: String): List<Int> {
	return File(fileName).readLines().map(String::toInt)
}

private fun readSample(): List<Int> {
	return sample.split("\n").map(String::toInt)
}

private fun testSamplePart1() {
	val resultExpected = 1
	val resultActual = solvePart1()
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	val resultExpected = 1
	val resultActual = solvePart2()
	if(resultExpected != resultActual) {
		println("Part2 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part2 sample passes") 
	}
}
