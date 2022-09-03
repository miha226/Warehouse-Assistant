package warehouse.assistant.domain.model

data class StorageCardDetails(
    val time:Long,
    val user:String,
    val storageCardItems:List<StorageCardItem>
)
