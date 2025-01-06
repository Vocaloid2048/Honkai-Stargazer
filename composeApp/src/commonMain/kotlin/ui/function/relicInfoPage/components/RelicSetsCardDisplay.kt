package ui.function.relicInfoPage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import files.RelicDetail
import files.Res
import files.phorphos_chats_circle_regular
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import type.ImageFolder
import type.Relic
import ui.components.TitleHeader
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.RELIC_CARD_HEIGHT
import utils.app.Constants.Companion.RELIC_CARD_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal12
import utils.app.TextColorNormalDim
import utils.app.newImageRequest

@Composable
fun RelicSetsCardDisplay(
    relicSetName: String,
    relicJson: JsonElement,
    isRelic: Boolean = true,
) {
    Column(
        modifier = Modifier.widthIn(INFO_MIN_WIDTH, INFO_MAX_WIDTH).fillMaxWidth().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)
    ) {
        TitleHeader(
            iconRId = Res.drawable.phorphos_chats_circle_regular, titleRId = Res.string.RelicDetail
        )

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in if (isRelic) { 1..4 } else { 5..6 }) {
                item{
                    Box(
                        modifier = Modifier.widthIn(RELIC_CARD_WIDTH,RELIC_CARD_WIDTH*1.5f).clip(
                            RoundedCornerShape(
                                topEnd = 15.dp,
                                topStart = 4.dp,
                                bottomEnd = 4.dp,
                                bottomStart = 4.dp
                            )
                        )
                    ) {
                        Column {
                            Box(
                                modifier = Modifier.defaultMinSize(
                                    RELIC_CARD_WIDTH, RELIC_CARD_HEIGHT
                                ).clip(
                                    RoundedCornerShape(
                                        topEnd = 15.dp,
                                        topStart = 4.dp,
                                        bottomEnd = 4.dp,
                                        bottomStart = 4.dp
                                    )
                                ).clickable(
                                    onClick = { },
                                    indication = ripple(),
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                            ) {
                                AsyncImage(
                                    model = newImageRequest(
                                        context = LocalPlatformContext.current,
                                        data = Relic.getRelicImageFromJSON(
                                            if (isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON,
                                            relicSetName, index
                                        )
                                    ),
                                    contentDescription = "Relic Icon",
                                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).background(
                                        Brush.verticalGradient(
                                            colors = getCardBgColorByRare(relicJson.jsonObject["rarity"]!!.jsonPrimitive.int)
                                        )
                                    ),
                                    contentScale = ContentScale.Crop

                                )
                            }
                            Row(
                                Modifier.fillMaxWidth().wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = relicJson.jsonObject["pieces"]!!.jsonObject[index.toString()]!!.jsonObject["name"]!!.jsonPrimitive.content,
                                    textAlign = TextAlign.Center,
                                    style = FontSizeNormal12(),
                                    color = TextColorNormalDim,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.wrapContentWidth()
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                }
            }
        }
    }
}