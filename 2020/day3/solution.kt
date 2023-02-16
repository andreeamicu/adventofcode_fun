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

val sampleSlopes = listOf(Slope(1, 1), Slope(3, 1), Slope(5, 1), Slope(7, 1), Slope(1, 2))

fun main() {
	testSamplePart1()
	testSamplePart2()
	val inputTerrain = Terrain(File("input.txt").readLines())
	val result1 = inputTerrain.countTrees(Slope(right = 3, down = 1))
	println("Answer part1: $result1")
	
	val result2 = inputTerrain.countTrees(sampleSlopes).product()
	println("Answer part2: $result2")
}

private fun List<Int>.product(): Long = fold(1) { product, element -> product.toLong() * element }

class Terrain(val lines: List<String>) {

	fun countTrees(slope: Slope): Int {
		val slots = mutableListOf<Slot>()
		var currentSlot = Slot(0, 0)
		
		while(currentSlot.row + slope.down < lines.size) {
			val row = currentSlot.row + slope.down
			val col = (currentSlot.col + slope.right) % lines[0].length
			
			currentSlot = Slot(row, col, lines[row][col])
			slots.add(currentSlot)
		}
		return slots.fold(0) { sum, slot -> sum + slot.treeCount }
	}

	fun countTrees(slopes: List<Slope>): List<Int> {
		return slopes.map { countTrees(it) }
	}

}

data class Slope(val right: Int, val down: Int)

data class Slot(val row: Int, val col: Int, val symbol: Char = '.') {
	val treeCount: Int
		get() = if (symbol == '#') 1 else 0
}

private fun testSamplePart1() {
	val resultExpected = 7
	val terrain = Terrain(sample.lines())
	val resultActual = terrain.countTrees(Slope(right = 3, down = 1))
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	/*
	Right 1, down 1.
	Right 3, down 1. (This is the slope you already checked.)
	Right 5, down 1.
	Right 7, down 1.
	Right 1, down 2.
	In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s); multiplied together, these produce the answer 336.
	*/
	
	val expectedTrees = listOf(2, 7, 3, 4, 2)
	val expectedProduct = 336L
	val terrain = Terrain(sample.lines())
	val countedTrees = terrain.countTrees(sampleSlopes)
	val product = countedTrees.product()

	if(expectedTrees != countedTrees) {
		println("Part2 sample fails! Got $countedTrees, expected $expectedTrees")
	} else if (product != expectedProduct) {
		println("Part2 sample fails! Got $product, expected $expectedProduct")		
	} else {
		println("Part2 sample passes") 
	}
}
