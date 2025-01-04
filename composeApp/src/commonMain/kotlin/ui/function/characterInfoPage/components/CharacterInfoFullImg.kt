package ui.function.characterInfoPage.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil3.Image
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import files.Res
import files.ico_lost_img
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import type.Character
import type.ImageFolder
import utils.app.newImageRequest

@Composable
fun CharacterInfoFullImg(
    modifier: Modifier = Modifier,
    fileName: String,
    isVisible: Boolean = true
) {

    val imageURL = mutableStateOf(Character.getCharacterImageFromFileName(ImageFolder.CHAR_FULL, fileName))
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).align(Alignment.BottomCenter)
        ) {
            AsyncImage(
                model = newImageRequest(
                    context = LocalPlatformContext.current,
                    data = imageURL.value,
                    crossFade = true
                ),
                contentDescription = "Character Full Image",
                contentScale = ContentScale.Fit,
                onError = { error ->
                    imageURL.value = Character.getCharacterImageFromFileName(ImageFolder.CHAR_SPLASH, fileName)
                },
                error = painterResource(Res.drawable.ico_lost_img)
            )
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