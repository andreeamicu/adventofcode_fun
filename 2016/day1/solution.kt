import java.io.File
import kotlin.math.*

/*
The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.

There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?

For example:

Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
R5, L5, R5, R3 leaves you 12 blocks away.
How many blocks away is Easter Bunny HQ?

--- Part Two ---
Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.

For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.

How many blocks away is the first location you visit twice?


*/

fun main() {
	testSamplePart1()
	testSamplePart2()
	val instructions = readInput(File("input.txt").readText())
	val navigation = instructions.navigate()
	val result1 = navigation.distanceToDestination()
	println("Answer part1: $result1")
	val result2 = navigation.revisited()
					.firstOrNull()
					?.absoluteDistance()
	println("Answer part2: $result2")
}

private fun List<Instruction>.navigate(): Navigation = this.fold(Navigation()) { navigation, instruction -> 
		navigation.move(instruction)
	}

data class Instruction(val direction: Char, val blocks: Int) {
	companion object {
		fun parse(entry: String) = Instruction(
			direction = entry.first(),
			blocks = entry.substring(1).toInt()
		)
	}
}


class Navigation {
	// 0 - N, 1 - E, 2 - S, 3 - W
	private val cardinalSigns = arrayOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0)) 
	// starts facing N, so Pair(0, 1) in index 0
	private var index: Int = 0
		private set
		
	val moves = mutableListOf<Pair<Int, Int>>()
	val visited = mutableListOf<Pair<Int, Int>>(Pair(0, 0))

	fun move(instruction: Instruction): Navigation {
		if (instruction.direction == 'R') {
			index = index + 1
		} else {
			index = index + 3 // turning left equals turning right 3 times
		}

		index = index % 4

		val currentSign: Pair<Int, Int> = cardinalSigns[index]
		moves.add(currentSign * instruction.blocks)

		var lastVisited = visited.last()
		for(i in 0 until instruction.blocks) {
			lastVisited = lastVisited + currentSign
			visited.add(lastVisited)
		}
		//println("visited: $visited")

		return this
	}

	fun distanceToDestination(): Int = moves.reduce { acc, move ->
		acc + move
	}.absoluteDistance()

	fun revisited(): Set<Pair<Int, Int>> {
		return visited
			.groupingBy { it }
			.eachCount()
			.filterValues { count -> count > 1 }
			.keys
			//?.also { println(it) }
	}

}

private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = Pair(first + other.first, second + other.second)

private operator fun Pair<Int, Int>.times(scalar: Int) = Pair(scalar * first, scalar * second)

private fun Pair<Int, Int>?.absoluteDistance() = if (this != null) abs(first) + abs(second) else 0

private fun readInput(text: String): List<Instruction> {
	return text.split(", ").map(Instruction::parse)
}

private fun testSamplePart1() {
	val inputs = listOf("R2, L3", "R2, R2, R2", "R5, L5, R5, R3", "R6, R5, R3, R2, R5")
	val expectedDistances = listOf(5, 2, 12, 11)

	val actualDistances = inputs.map { text -> 
		readInput(text).navigate().distanceToDestination()
	}

	if (expectedDistances != actualDistances) {
		println("Part1 sample fails! Got $actualDistances, expected $expectedDistances")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	//For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.

	val inputs = listOf("R2, L3", "R2, R2, R2", "R5, L5, R5, R3", "R6, R5, R3, R2, R5", "R8, R4, R4, R8")
	val expectedRevisited: List<Pair<Int, Int>?> = listOf(null, null, null, Pair(6, -3), Pair(4, 0) )
	val expectedDistances = listOf(null, null, null, 9, 4)

	val revisited = inputs.map { text -> 
		readInput(text)
			.navigate()
			.revisited()
			.firstOrNull()
	}
	val firstRevisitedDistances = revisited.map { it?.absoluteDistance() }

	if (expectedRevisited != revisited) {
		println("Part2 sample fails! Got $revisited, expected $expectedRevisited")
	} else if (expectedDistances != firstRevisitedDistances) {
		println("Part2 sample fails! Got $firstRevisitedDistances, expected $expectedDistances")
	} else {
		println("Part2 sample passes") 
	}
}
