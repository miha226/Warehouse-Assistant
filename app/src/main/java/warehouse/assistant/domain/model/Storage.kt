package warehouse.assistant.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    val storageName:String = "0"
):Parcelable
