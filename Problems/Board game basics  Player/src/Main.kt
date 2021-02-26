class Player(val id: Int, val name: String, val hp: Int){
    companion object {
        var id = 100
        fun create(name: String): Player{
            id++
            return Player(id, name, 100)
        }
    }
}