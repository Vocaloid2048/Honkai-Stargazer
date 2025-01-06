package ui.function.lightconeInfoPage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import files.Res
import files.bg_lightcone_artwork_back
import files.bg_lightcone_artwork_front
import org.jetbrains.compose.resources.painterResource
import type.ImageFolder
import type.Lightcone

@Composable
fun LightconeInfoFullImageContent(
    modifier: Modifier = Modifier,
    fileName: String,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.bg_lightcone_artwork_back),
            modifier = modifier.offset((12).dp, (12).dp),
            contentDescription = "Lightcone Back Image",
            contentScale = ContentScale.FillBounds,
        )
        AsyncImage(
            model = Lightcone.getLightconeImageFromJSON(
                ImageFolder.LC_ARTWORK, fileName
            ),
            modifier = modifier,
            contentDescription = "Lightcone Full Image",
            contentScale = ContentScale.Fit,
        )
        Image(
            painter = painterResource(Res.drawable.bg_lightcone_artwork_front),
            contentDescription = "Lightcone Front Image",
            modifier = modifier.offset((-12).dp, (-12).dp),
            contentScale = ContentScale.FillBounds,
        )

    }
}