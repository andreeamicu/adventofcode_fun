import java.io.File

/*

Below the message, the device shows a sequence of changes in frequency (your puzzle input). A value like +6 means the current frequency increases by 6; a value like -3 means the current frequency decreases by 3.

For example, if the device displays frequency changes of +1, -2, +3, +1, then starting from a frequency of zero, the following changes would occur:

Current frequency  0, change of +1; resulting frequency  1.
Current frequency  1, change of -2; resulting frequency -1.
Current frequency -1, change of +3; resulting frequency  2.
Current frequency  2, change of +1; resulting frequency  3.
In this example, the resulting frequency is 3.

Here are other example situations:

+1, +1, +1 results in  3
+1, +1, -2 results in  0
-1, -2, -3 results in -6
Starting with a frequency of zero, what is the resulting frequency after all of the changes in frequency have been applied?

--- Part Two ---
You notice that the device repeats the same frequency change list over and over. To calibrate the device, you need to find the first frequency it reaches twice.

For example, using the same list of changes above, the device would loop as follows:

Current frequency  0, change of +1; resulting frequency  1.
Current frequency  1, change of -2; resulting frequency -1.
Current frequency -1, change of +3; resulting frequency  2.
Current frequency  2, change of +1; resulting frequency  3.
(At this point, the device continues from the start of the list.)
Current frequency  3, change of +1; resulting frequency  4.
Current frequency  4, change of -2; resulting frequency  2, which has already been seen.
In this example, the first frequency reached twice is 2. Note that your device might need to repeat its list of frequency changes many times before a duplicate frequency is found, and that duplicates might be found while in the middle of processing the list.

Here are other examples:

+1, -1 first reaches 0 twice.
+3, +3, +4, -2, -4 first reaches 10 twice.
-6, +3, +8, +5, -6 first reaches 5 twice.
+7, +7, -2, -7, -4 first reaches 14 twice.
What is the first frequency your device reaches twice?

*/


fun main() {
	testSamplePart1()
	testSamplePart2()
	val commands = File("input.txt").readLines().toCommands()
	val result1 = commands.total()
	println("Answer part1: $result1")
	val result2 = commands.firstDuplicate()
	println("Answer part2: $result2")
}

private fun List<String>.toCommands() = map { Command.parse(it) }

private fun List<Command>.total(): Int {
	return fold(0) { total, command -> 
			command.compute(total)
		}
}

private fun List<Command>.firstDuplicate(): Int {
	val frequencies = HashSet<Int>(size)
	var current = 0
	var index = 0

	do {

		frequencies.add(current)
		current = this[index].compute(current) 
		index = (index + 1) % size
	
	} while (!frequencies.contains(current))

	return current
}

sealed class Command(val value: Int, val compute: (Int) -> Int) {

	class Add(value: Int): Command(value, { x -> x + value })

	class Subtract(value: Int): Command(value, { x -> x - value })

	companion object {
		fun parse(input: String): Command {
			val value = input.substring(1).toInt()
			return if (input.first() == '+') {
				Add(value)
			} else {
				Subtract(value)
			}
		}
	}

}

private fun testSamplePart1() {
	/*
	For example, if the device displays frequency changes of +1, -2, +3, +1, 
	then starting from a frequency of zero, the following changes would occur:

	Current frequency  0, change of +1; resulting frequency  1.
	Current frequency  1, change of -2; resulting frequency -1.
	Current frequency -1, change of +3; resulting frequency  2.
	Current frequency  2, change of +1; resulting frequency  3.
	In this example, the resulting frequency is 3.

	Here are other example situations:

	+1, +1, +1 results in  3
	+1, +1, -2 results in  0
	-1, -2, -3 results in -6
	*/

	val samples = listOf("+1, -2, +3, +1", "+1, +1, +1", "+1, +1, -2", "-1, -2, -3")
	val resultExpected = listOf(3, 3, 0, -6)
	val resultActual = samples.map { input -> input.split(", ").toCommands().total() }

	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	/*
	+1, -2, +3, +1 first reaches 2
	+1, -1 first reaches 0 twice.
	+3, +3, +4, -2, -4 first reaches 10 twice.
	-6, +3, +8, +5, -6 first reaches 5 twice.
	+7, +7, -2, -7, -4 first reaches 14 twice.
	*/

	val samples = listOf("+1, -2, +3, +1", "+1, -1", "+3, +3, +4, -2, -4", "-6, +3, +8, +5, -6", "+7, +7, -2, -7, -4")
	val resultExpected = listOf(2, 0, 10, 5, 14)
	val resultActual = samples.map { input -> input.split(", ").toCommands().firstDuplicate() }
	
	if(resultExpected != resultActual) {
		println("Part2 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part2 sample passes") 
	}
}
