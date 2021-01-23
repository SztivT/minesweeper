import java.util.*
import kotlin.math.pow

fun main() {
    // put your code here
    val scanner = Scanner(System.`in`)
    val end = scanner.nextInt()
    var n = 0
    var base = 1.0
    do {
        n = base.pow(2).toInt()
        if (end >= n) {
            println(n)
        }
        base++
    } while (end > n)
}