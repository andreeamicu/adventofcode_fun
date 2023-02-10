import java.io.File

/*

Part 1

Santa is trying to deliver presents in a large apartment building, but he can't find the right floor - the directions he got are a little confusing. He starts on the ground floor (floor 0) and then follows the instructions one character at a time.

An opening parenthesis, (, means he should go up one floor, and a closing parenthesis, ), means he should go down one floor.

The apartment building is very tall, and the basement is very deep; he will never find the top or bottom floors.

For example:

(()) and ()() both result in floor 0.
((( and (()(()( both result in floor 3.
))((((( also results in floor 3.
()) and ))( both result in floor -1 (the first basement level).
))) and )())()) both result in floor -3.
To what floor do the instructions take Santa?

--- Part Two ---

Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1). The first character in the instructions has position 1, the second character has position 2, and so on.

For example:

) causes him to enter the basement at character position 1.
()()) causes him to enter the basement at character position 5.
What is the position of the character that causes Santa to first enter the basement?

*/


fun main() {
	testSamplePart1()
	testSamplePart2()
	val input = readInput("input.txt")
	val result1 = input.countFloors()
	println("Answer part1: $result1")
	val result2 = input.firstBasementPosition()
	println("Answer part2: $result2")
}

private fun calculateFloor(currentFloor: Int, symbol: Char) =
	when(symbol) {
		'(' -> currentFloor + 1
		')' -> currentFloor - 1
		else -> 0
	}

private fun String.countFloors(): Int {
	return fold(0) { currentFloor, symbol -> calculateFloor(currentFloor, symbol)	}
}

private fun String.firstBasementPosition(): Int {
	return scanIndexed(Instruction()) { index, instruction, symbol -> 
		Instruction(index + 1, calculateFloor(instruction.floor, symbol))
	}
	.drop(1)
	//.also { println(it) }
	.firstOrNull { it.floor == -1 }
	?.let { it.position } ?: 0
}

data class Instruction(val position: Int = 0, val floor: Int = 0)

private fun readInput(fileName: String): String {
	return File(fileName).readText()
}

private fun testSamplePart1() {
	val samples = listOf(
		"(())",
		"()()",
		"(((",
		"(()(()("
		,"))(((((",
		"())",
		"))(",
		")))",
		")())())"
	)
	val sampleFloors = listOf(
		0,
		0,
		3,
		3,
		3,
		-1,
		-1,
		-3,
		-3
	)

	//println(sampleFloors.zip(samples).joinToString(separator = "") { (floor, pattern) -> "$pattern .... $floor\n" })
	val floors = samples.map { it.countFloors() }

	if(sampleFloors == floors) {
		println("Part1 sample passes") 
	} else {
		println("Part1 sample fails! Got $floors, expected $sampleFloors")
	}
}

private fun testSamplePart2() {
	/*
		) causes him to enter the basement at character position 1.
		()()) causes him to enter the basement at character position 5.
	*/
	val samples = listOf(")", "()())")
	val samplesPositions = listOf(1, 5) 

	val positions = samples.map { it.firstBasementPosition() }

	if(samplesPositions == positions) {
		println("Part2 sample passes") 
	} else {
		println("Part2 sample fails! Got $positions, expected $samplesPositions")
	}
}
