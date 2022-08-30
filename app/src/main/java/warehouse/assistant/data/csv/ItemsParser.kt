package warehouse.assistant.data.csv

import android.content.ContentValues
import android.util.Log
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import warehouse.assistant.domain.model.Item
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Double
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsParser @Inject constructor(): CSVParser<Item> {
    override suspend fun parse(stream: InputStream): List<Item> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            Log.d(ContentValues.TAG,"Document data3 usao u coroutinu")
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val itemId = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val EAN = line.getOrNull(6)
                    val priceString = line.getOrNull(11)?.filter { !it.isWhitespace() }?.replace(",","")
                    val price = Double(priceString).toDouble()

                    Item(
                        itemId = itemId ?: return@mapNotNull null,
                        name = name ?: return@mapNotNull null,
                        EAN = EAN ?: return@mapNotNull null,
                        price = price ?: return@mapNotNull null
                    )
                }.also { csvReader.close()
                    var test = it.isEmpty()
                    Log.d(ContentValues.TAG,"Document data33 dosao je do pocetka ${it.get(0).toString()} aha")
                return@withContext it}
        }
    }
}