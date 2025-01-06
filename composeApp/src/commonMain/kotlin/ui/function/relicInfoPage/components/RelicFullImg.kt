package ui.function.relicInfoPage.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import getScreenSizeInfo
import type.ImageFolder
import type.Relic
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.newImageRequest

@Composable
fun RelicInfoFullImg(
    modifier: Modifier = Modifier,
    fileName: String,
    isRelic: Boolean = false,
    isVisible: Boolean = true
) {
    //val iconSize = (getScreenSizeInfo().wDP - 36.dp - 32.dp)/2;
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(Alignment.Center)
        ) {
            Column(modifier = Modifier.width(getScreenSizeInfo().wDP - 36.dp).aspectRatio(1f).sizeIn(
                INFO_MIN_WIDTH, INFO_MAX_WIDTH
            ).padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)) {
                Row{
                    AsyncImage(
                        model = newImageRequest(
                            context = LocalPlatformContext.current,
                            data = Relic.getRelicImageFromJSON(if(isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON, fileName, if(isRelic) 1 else 5)
                        ),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    AsyncImage(
                        model = newImageRequest(
                            context = LocalPlatformContext.current,
                            data = Relic.getRelicImageFromJSON(if(isRelic) ImageFolder.RELIC_ICON else ImageFolder.ORMANENT_ICON, fileName, if(isRelic) 2 else 6),
                        ),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                    )
                }
                Row {
                    if(isRelic){
                        AsyncImage(
                            model = newImageRequest(
                                context = LocalPlatformContext.current,
                                data = Relic.getRelicImageFromJSON(ImageFolder.RELIC_ICON, fileName, 3)
                            ),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = newImageRequest(
                                context = LocalPlatformContext.current,
                                data = Relic.getRelicImageFromJSON(ImageFolder.RELIC_ICON, fileName, 4)
                            ),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp).weight(1f).fillMaxWidth().aspectRatio(1f)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f).background(
                Brush.verticalGradient(
                    colors = listOf(Color(0x00000000), Color(0xCC000000))
                )
            ).align(Alignment.BottomCenter),
        )

    }
}