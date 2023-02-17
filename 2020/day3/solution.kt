import java.io.File

/*
--- Dacol 3: Toboggan Trajectorcol ---
With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:

..##.........##.........##.........##.........##.........##.......  --->
#..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........X.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...#X....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
In this example, traversing the map using this slope would cause you to encounter 7 trees.

Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?

--- Part Two ---
Time to check the rest of the slopes - you need to minimize the probability of a sudden arboreal stop, after all.

Determine the number of trees you would encounter if, for each of the following slopes, you start at the top-left corner and traverse the map all the way to the bottom:

Right 1, down 1.
Right 3, down 1. (This is the slope you already checked.)
Right 5, down 1.
Right 7, down 1.
Right 1, down 2.
In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s) respectively; multiplied together, these produce the answer 336.

What do you get if you multiply together the number of trees encountered on each of the listed slopes?

*/


val sample = """
..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
""".trimMargin()

val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)

fun main() {
	testSamplePart1()
	testSamplePart2()

	val lines = File("input.txt").readLines()
	val result1 = countTrees(lines, 3 to 1)
	println("Answer part1: $result1")
	
	val result2 = countTrees(lines, slopes).product()
	println("Answer part2: $result2")
}

private fun countTrees(lines: List<String>, slope: Pair<Int, Int>): Int {
	val patternLength = lines[0].length
	val (rightStep, downStep) = slope 
	val slots = mutableListOf<Char>()
	for(row in 0 until lines.size step downStep) {
		val currentSteps = row / downStep
		val col = (currentSteps * rightStep) % patternLength
		slots.add(lines[row][col])
	}
	return slots.count { it == '#' }
}

private fun countTrees(lines: List<String>, slopes: List<Pair<Int, Int>>): List<Int> {
	return slopes
		.map { slope -> countTrees(lines, slope) }
}

private fun List<Int>.product() = fold(1L) { x, y -> x.toLong() * y }

private fun testSamplePart1() {
	val resultExpected = 7
	val resultActual = countTrees(sample.lines(), 3 to 1)
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	val expectedTrees = listOf(2, 7, 3, 4, 2)
	val expectedProduct = 336L
	val lines = sample.lines()
	val countedTrees = countTrees(lines, slopes)
	val product = countedTrees.product()

	if(expectedTrees != countedTrees) {
		println("Part2 sample fails! Got $countedTrees, expected $expectedTrees")
	} else if (product != expectedProduct) {
		println("Part2 sample fails! Got $product, expected $expectedProduct")		
	} else {
		println("Part2 sample passes") 
	}
}
