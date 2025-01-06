package ui.function.relicInfoPage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import files.Res
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.painterResource
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.pxToDp

@Composable
fun RelicBasicInfo(infoJson: JsonElement){

    var columnHeightDp by remember { mutableStateOf(110.dp) }
    val density = LocalDensity.current.density
    val itemName = remember { infoJson.jsonObject["name"]!!.jsonPrimitive.content }
    val itemRarity = remember { infoJson.jsonObject["rarity"]!!.jsonPrimitive.int }

    Column {
        Box(modifier = Modifier.height(getScreenSizeInfo().hDP - columnHeightDp))

        Column(modifier = Modifier.padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
            .onSizeChanged { item ->
                columnHeightDp = pxToDp(item.height, density)
            }) {
            Row() {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = itemName,
                    style = FontSizeNormal14(),
                    fontSize = 32.sp,
                    color = Color.White,
                )
            }

            Box(Modifier.height(8.dp))

            Row {
                repeat(itemRarity) {
                    Image(
                        modifier = Modifier.size(24.dp,28.dp),
                        painter = painterResource(Res.drawable.ui_icon_star),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = "Stars to represent Rarity"
                    )
                }
            }
        }
    }
}