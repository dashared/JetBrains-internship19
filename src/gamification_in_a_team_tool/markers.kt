package gamification_in_a_team_tool

data class TextRange(val start: Int, val endExclusive: Int)

fun findMarkerIgnoringSpace(text: String, marker: String): TextRange? {

    val markerWithoutWhiteSpace = marker.withoutWhiteSpaces()

    if (markerWithoutWhiteSpace.isEmpty())
        return null

    // -1 if not found
    var start: Int = text.indexOf(markerWithoutWhiteSpace.first())
    var endExclusive: Int = text.indexOf(markerWithoutWhiteSpace.last())

    while (start < endExclusive && start != -1 && endExclusive != -1)
    {
        var currentSlice = text.substring(start..endExclusive).withoutWhiteSpaces()

        if (currentSlice == markerWithoutWhiteSpace) {
            return TextRange(start, endExclusive + 1)
        }

        start = text.indexOf(char = markerWithoutWhiteSpace.first(), startIndex = start + 1)
        endExclusive = text.indexOf(char = markerWithoutWhiteSpace.last(), startIndex = start + 1)
    }

    return null
}

fun String.withoutWhiteSpaces(): String {
    return this.filter { !it.isWhitespace() }
}

fun main() {
    // test from the task
    findMarkerIgnoringSpace("Text [1299bba / 0 0 0 0 1] from David", "[1299 bba/00001]") shouldParse TextRange(5,26)

    // my tests
    findMarkerIgnoringSpace("I am [h h \r jj]","[hhjj]") shouldParse TextRange(5,15)
    findMarkerIgnoringSpace("hhhhhh dd d dddhh","jjjj").shouldFail()
    findMarkerIgnoringSpace("hhhhhh [dd] d dddhh","[]").shouldFail()
    findMarkerIgnoringSpace("","[]").shouldFail()
    findMarkerIgnoringSpace("[dsjsskd]","").shouldFail()
    findMarkerIgnoringSpace("[Text [1299bba / 0 0 0 0 1]] from  [1299bba / 0 0 0 0 1]David", "[1299 bba/00001]") shouldParse TextRange(6,27)
    findMarkerIgnoringSpace("[[I am] [h h \r jj]","[I am]") shouldParse TextRange(1,7)
}

var logIdx = 0

infix fun TextRange?.shouldParse(success: TextRange?) =
    if (this != success) throw AssertionError("Expected $success, but got $this")
    else println("${logIdx++}: Parsed: $this")

fun TextRange?.shouldFail() =
    if (this != null) throw AssertionError("Expected null, but got $this")
    else println("${logIdx++}: Parsed: $this")


