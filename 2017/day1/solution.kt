import java.io.File

/*
The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.

For example:

1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
1111 produces 4 because each digit (all 1) matches the next.
1234 produces 0 because no digit matches the next.
91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
What is the solution to your captcha?

--- Part Two ---
You notice a progress bar that jumps to 50% completion. Apparently, the door isn't yet satisfied, but it did emit a star as encouragement. The instructions change:

Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.

For example:

1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
1221 produces 0, because every comparison is between a 1 and a 2.
123425 produces 4, because both 2s match each other, but no other digit has a match.
123123 produces 12.
12131415 produces 4.
What is the solution to your new captcha?

*/

fun main() {
	testSamplePart1()
	testSamplePart2()
	val input: List<Int> = File("input.txt").readText().toIntList()
	val result1 = input.matchingDigits(1).sum()
	println("Answer part1: $result1")
	val result2 = input.matchingDigits(input.size / 2).sum()
	println("Answer part2: $result2")
}

private fun List<Int>.matchingDigits(step: Int): List<Int> =
	foldIndexed(ArrayList<Int>()) { i, list, element -> 
		val next = this[ (i + step) % size ]
		if (element == next) {
			list.add(element)
		}
		list
	}

private fun String.toIntList(): List<Int> {
	return this.toList()
		.map { it.digitToInt() }
}

private fun testSamplePart1() {
	/*
	1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
	1111 produces 4 because each digit (all 1) matches the next.
	1234 produces 0 because no digit matches the next.
	91212129 produces 9 because the only digit that matches the next one is the last digit, 9.
	*/
	
	val samples = listOf("1122", "1111", "1234","91212129")
	val resultExpected = listOf(3, 4, 0, 9)
	val resultActual = samples.map { input -> input.toIntList().matchingDigits(1).sum() }
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	/*
	1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
	1221 produces 0, because every comparison is between a 1 and a 2.
	123425 produces 4, because both 2s match each other, but no other digit has a match.
	123123 produces 12.
	12131415 produces 4.
	*/

	val samples = listOf("1212", "1221", "123425","123123", "12131415")
	val resultExpected = listOf(6, 0, 4, 12, 4)
	val resultActual = samples.map { it.toIntList().matchingDigits(it.length / 2).sum() }
	if(resultExpected != resultActual) {
		println("Part2 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part2 sample passes") 
	}
}
