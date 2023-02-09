import java.io.File

/*
Day 1 / 2020

Part 1

Find the two entries that sum to 2020 and then multiply those two numbers together.
For example, suppose your expense report contained the following:

1721
979
366
299
675
1456

In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.

Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together? 

------

Part 2
Find three numbers in your expense report that meet the same criteria.

Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.

In your expense report, what is the product of the three entries that sum to 2020?

*/

/*

1721 -- 299
979  --
366 --
299 -- 1721
675 --
1456 --

---------

1721
979 -- 366, 675
366 -- 979, 675
299
675 -- 979, 366
1456

*/

val sample = """
1721
979
366
299
675
1456
""".trimMargin()

const val TOTAL = 2020

fun main() {
	testSamplePart1()
	testSamplePart2()

	val numbers = readInput("input.txt")
	val result1 = productOfPair(numbers, TOTAL)
	val result2 = productOfTriple(numbers, TOTAL)
	println("Answer part 1: $result1")
	println("Answer part 2: $result2")
}

private fun productOfPair(numbers: List<Int>, total: Int): Long {
	val complementPairs = findComplementPairs(numbers, total)
	val complementProduct = complementPairs.first().let { (a, b) -> a.toLong() * b }
	return complementProduct
}

private fun findComplementPairs(numbers: List<Int>, total: Int): List<Pair<Int, Int>> {
	val complements = numbers.associateBy { total - it}
	//println("complements are $complements")

	val complementPairs = numbers.mapNotNull { number ->
		complements[number]?.let { complement -> Pair(number, complement) }
	}
	// println("complementPairs $complementPairs")
	return complementPairs
}


private fun findComplementTriples(numbers: List<Int>, total: Int): List<Triple<Int, Int, Int>?> {
	val complementSums = numbers.associateBy { total - it }
	//println("complementSums: $complementSums")

	val complementTriples: List<Triple<Int, Int, Int>?> = complementSums.map { (sum, number) -> 
		findComplementPairs(numbers, sum).firstOrNull()?.let { (first, second) ->
			Triple(first, second, number)
		}
	}
	//println("complementTriples: $complementTriples")

	return complementTriples
}


private fun productOfTriple(numbers: List<Int>, total: Int): Long {
	val complementTriples: List<Triple<Int, Int, Int>?> = findComplementTriples(numbers, total)
	val complementProduct = complementTriples.filterNotNull().firstOrNull()?.let { (a, b, c) -> a.toLong() * b * c } ?: 0L
	return complementProduct
}

private fun readInput(fileName: String): List<Int> {
	return File(fileName).readLines().map(String::toInt)
}

private fun readSample(): List<Int> {
	return sample.split("\n").map(String::toInt)
}

private fun testSamplePart1() {
	val numbers = readSample()
	//println("sample size is ${numbers.size} numbers")
	val resultExpected = 514579L
	val resultActual = productOfPair(numbers, TOTAL)
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	val numbers = readSample()
	val resultExpected = 241861950L
	val resultActual = productOfTriple(numbers, TOTAL)
	if(resultExpected != resultActual) {
		println("Part2 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part2 sample passes") 
	}
}