package warehouse.assistant.domain.model

data class StorageCardItem(
    val time: Long,
    val itemId: String,
    val forSaleInput: Boolean = true,
    val forServiceInput: Boolean = true,
    val quantityForSale: Int = 0,
    val quantityForService: Int = 0
)
