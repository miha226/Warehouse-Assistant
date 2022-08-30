package warehouse.assistant.domain.model

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import warehouse.assistant.presentation.destinations.DirectionDestination

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription:String,
    val icon: Int,
    val route: DirectionDestination
)
