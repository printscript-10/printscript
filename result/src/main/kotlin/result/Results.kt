package result

sealed interface Result

interface Success : Result
interface Failure : Result {
    val error: String
}
