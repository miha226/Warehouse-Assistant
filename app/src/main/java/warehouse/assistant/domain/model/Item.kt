package warehouse.assistant.domain.model

data class Item(
    val itemId: String,
    val EAN:String,
    val name:String,
    val price:Double
){
    override fun toString(): String {
        return itemId + " " + " " + name+ " " + EAN + " " + price.toString()
    }
}