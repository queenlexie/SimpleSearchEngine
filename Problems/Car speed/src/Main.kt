import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val speed = scanner.nextInt()
    val limit = scanner.next()
    if(scanner.hasNext())
        checkLimit(speed)
    else
        checkLimit(speed, limit = limit.toInt())
}
fun checkLimit(speed : Int, limit : Int = 60) {
    val difference = speed - limit
    if(difference <= 0 )
        println("Within the limit")
    else
        println("Exceeds the limit by $difference kilometers per hour")
}