import java.io.File

val sample = """
1721
979
366
299
675
1456
""".trimMargin()

const val TOTAL = 2020

/*
1721 -- 299
979  --
366 --
299 -- 1721
675 --
1456 --

*/


fun main() {
	testSamplePart1()
	testSamplePart2()
	
	//println("complementProduct: $complementProduct")
}

private fun solvePart1(numbers: List<Int>, total: Int): Long {
	val complements = numbers.associateBy { total - it}
	//println("complements are $complements")

	val complementPairs = numbers.mapNotNull { number ->
		complements[number]?.let { complement -> Pair(number, complement) }
	}
	// println("complementPairs $complementPairs")

	val complementProduct = complementPairs.first().let { (a, b) -> a.toLong() * b }
	//println("complementProduct: $complementProduct")

	return complementProduct
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
	//val numbers = readInput("sample.txt")
	val numbers = readSample()
	//println("sample size is ${numbers.size} numbers")
	val resultExpected = 514579L
	val resultActual = solvePart1(numbers, TOTAL)
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