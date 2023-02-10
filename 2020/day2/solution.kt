import java.io.File

/*

Part 1

You have the following list:

1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc

Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

How many passwords are valid according to their policies?

-----

Part 2

Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

Given the same example list from above:

1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
How many passwords are valid according to the new interpretation of the policies?

*/

val sample = """
1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
""".trimMargin()

val policyRegex = """(\d+)-(\d+) (\w): (\w+)""".toRegex()

fun main() {
	testSamplePart1()
	testSamplePart2()
	val entries = readInput("input.txt")
	val result1 = validPasswordsCountPart1(entries)
	println("Answer part1: $result1")
	val result2 = validPasswordsCountPart2(entries)
	println("Answer part2: $result2")
}

private fun validPasswordsCountPart1(entries: List<Entry>): Int {
	val validPasswords = entries.filter { entry -> 
		val letterCount = entry.password.count { it == entry.letter }
		letterCount in entry.policy.min..entry.policy.max
	}
	//println("validPasswords: ${printEntries(validPasswords)}")
	return validPasswords.size
}

private fun validPasswordsCountPart2(entries: List<Entry>): Int {
	val validPasswords = entries.filter { entry -> 
		entry.policy.let{ (min, max) -> 
			val letterInFirstPosition = entry.password.getOrNull(min - 1) == entry.letter
			val letterInSecondPosition = entry.password.getOrNull(max - 1) == entry.letter
			letterInFirstPosition xor letterInSecondPosition
		}
	}
	//println("validPasswords: ${printEntries(validPasswords)}")
	return validPasswords.size	
}

data class Entry(
	val password: String,
	val letter: Char,
	val policy: Policy
)

data class Policy(val min: Int, val max: Int)

private fun readInput(fileName: String): List<Entry> {
	return File(fileName).readLines().map { parseEntryLine(it) }
}

private fun readSample(): List<Entry> {
	return sample.split("\n").map { line -> 
		parseEntryLine(line)
	}
}

private fun parseEntryLine(line: String): Entry {
	val(min, max, letter, password) = policyRegex.find(line)!!.destructured
	return Entry(password, letter.first(), Policy(min.toInt(), max.toInt()))
}

private fun printEntries(entries: List<Entry>): String {
	return entries.joinToString("\n") + "\n"
}

private fun testSamplePart1() {
	val entries = readSample()

	//println("Sample read: ${printEntries(entries)}")

	val resultExpected = 2
	val resultActual = validPasswordsCountPart1(entries)
	if(resultExpected != resultActual) {
		println("Part1 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part1 sample passes") 
	}
}

private fun testSamplePart2() {
	val entries = readSample()
	val resultExpected = 1
	val resultActual = validPasswordsCountPart2(entries)
	if(resultExpected != resultActual) {
		println("Part2 sample fails! Got $resultActual, expected $resultExpected")
	} else {
		println("Part2 sample passes") 
	}
}
