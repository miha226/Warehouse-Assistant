package warehouse.assistant.presentation.app_navigation

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import warehouse.assistant.R

@Composable
fun QRCodeButton(
    modifier: Modifier = Modifier,
    buttonClicked: () -> Unit
){
    ElevatedButton(onClick = {
        Log.d(ContentValues.TAG,"Clicked on floating button")},
        modifier = Modifier
            .height(70.dp), elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_qr_code_scan_icon),
            contentDescription = "qr code scanner", modifier = Modifier.width(30.dp))
    }
}