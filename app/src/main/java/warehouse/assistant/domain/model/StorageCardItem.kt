package warehouse.assistant.domain.model

data class StorageCardItem(
    val time: Long = 1,
    val itemId: String = "0",
    val forSaleInput: Boolean = true,
    val forServiceInput: Boolean = true,
    val quantityForSale: Int = 0,
    val quantityForService: Int = 0,
    val price: Double = 0.0
)
