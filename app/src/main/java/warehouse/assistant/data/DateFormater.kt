package warehouse.assistant.data

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate

object DateFormater {

    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss")

    fun getCurrentMillis():Long{
        return Timestamp(System.currentTimeMillis()).time
    }

    fun getDateFromMillis(millis:Long):String{
        return simpleDateFormat.format(millis)
    }


}