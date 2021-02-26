fun main()  = readLine()!!
    .let { if (it == "HIDDEN") greeting()
            else greeting(it) }
fun greeting(name : String = "secret user")
        = println("Hello, $name!")