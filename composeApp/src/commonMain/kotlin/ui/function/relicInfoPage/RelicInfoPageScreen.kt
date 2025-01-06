package ui.function.relicInfoPage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import components.InfoNavigateItem
import components.InfoNavigatorBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import files.RelicDetail
import files.RelicStatus2Pcs
import files.RelicStatus4Pcs
import files.Res
import files.ic_favourite_btn
import files.phorphos_chats_circle_regular
import files.phorphos_dice_four_regular
import files.phorphos_dice_two_regular
import files.phorphos_person_fill
import files.ui_icon_star
import getScreenSizeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import type.ImageFolder
import type.Relic
import ui.components.BackIcon
import ui.components.HeaderData
import ui.components.InfoDisplayDialog
import ui.components.PAGE_HEADER_HEIGHT
import ui.components.PageHeader
import ui.components.RelicCard
import ui.components.TitleHeader
import ui.components.defaultHeaderData
import ui.function.relicInfoPage.components.RelicBasicInfo
import ui.function.relicInfoPage.components.RelicInfoFullImg
import ui.function.relicInfoPage.components.RelicSetInfo
import ui.function.relicInfoPage.components.RelicSetsCardDisplay
import utils.app.Constants.Companion.INFO_MAX_WIDTH
import utils.app.Constants.Companion.INFO_MIN_WIDTH
import utils.app.Constants.Companion.RELIC_CARD_HEIGHT
import utils.app.Constants.Companion.RELIC_CARD_WIDTH
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.Constants.Companion.getCardBgColorByRare
import utils.app.FontSizeNormal12
import utils.app.FontSizeNormal14
import utils.app.JsonElementSaver
import utils.app.Language
import utils.app.TextColorNormalDim
import utils.app.htmlDescApplier
import utils.app.newImageRequest
import utils.app.pxToDp

private lateinit var localCoroutineScope: CoroutineScope;
private lateinit var localSnackbarHostState: SnackbarHostState;

val relicInfoNavItemList = arrayOf<InfoNavigateItem>(
    InfoNavigateItem(Res.drawable.phorphos_dice_two_regular, 1, Res.string.RelicStatus2Pcs),
    InfoNavigateItem(Res.drawable.phorphos_dice_four_regular, 2, Res.string.RelicStatus4Pcs),
    InfoNavigateItem(Res.drawable.phorphos_chats_circle_regular, 3, Res.string.RelicDetail),
)

private const val scrollPxTrigInvisible = 250f

@OptIn(FlowPreview::class)
@Composable
fun RelicInfoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
    backStackEntry: BackStackEntry,
    snackbarHostState: SnackbarHostState? = remember { SnackbarHostState() },
) {

    var density = LocalDensity.current.density
    val relicName = backStackEntry.path<String>("relicName")!!.replace("_", " ")
    val relicFileName = backStackEntry.query<String>("fileName")!!

    val hazeState = remember { HazeState() }
    val relicInfoJson : JsonElement by rememberSaveable(stateSaver = JsonElementSaver) { mutableStateOf(
        Relic.getRelicDataFromJSON(relicFileName, Language.TextLanguageInstance) as JsonElement) }

    localCoroutineScope = rememberCoroutineScope();
    localSnackbarHostState = snackbarHostState!!;

    val headerDataPage = HeaderData(
        relicInfoJson.jsonObject["name"]!!.jsonPrimitive.content,
        titleIconId = Res.drawable.phorphos_person_fill
    )

    val listState = rememberLazyListState()

    var isNaviBarVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollBackward }
            .distinctUntilChanged()
            .collect {
                isNaviBarVisible = it
            }
    }

    val dialogComponent : MutableState<@Composable () -> Unit> = remember { mutableStateOf({}) }
    val dialogDisplay = remember { mutableStateOf(false) }
    val dialogLastTrigType = remember { mutableStateOf("NONE") }
    val dialogTitle = remember { mutableStateOf("Nope") }

    Box {

        val isRelic = relicFileName.toInt() < 300
        RelicInfoFullImg(
            fileName = relicName,
            isVisible = !isNaviBarVisible, //alpha = scrollToAlpha
            isRelic = isRelic
        )

        //RecycleView
        LazyColumn(state = listState, modifier = Modifier.haze(hazeState).align(Alignment.Center).navigationBarsPadding()) {
            item { RelicBasicInfo(relicInfoJson) }
            item { RelicSetInfo(relicInfoJson, false) }
            //Don't forget to add "StatusBarPadding" !
            item { if(isRelic) RelicSetInfo(relicInfoJson, isRelic) }
            item { RelicSetsCardDisplay(relicName, relicInfoJson, isRelic) }
            item { Box(modifier = Modifier.height(72.dp)) }
        }

        PageHeader(
            navigator = navigator,
            headerData = headerDataPage,
            hazeState = hazeState,
            backIconId = BackIcon.CANCEL,
            forwardIconId = Res.drawable.ic_favourite_btn,
            onForward = {}
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if(dialogDisplay.value){
                InfoDisplayDialog(dialogTitle.value, dialogComponent.value, modifier = Modifier.align(Alignment.BottomCenter), hazeState, isNavBarVisible = (isNaviBarVisible), isDialogVisible = (dialogDisplay))
            } else {
                InfoNavigatorBar(relicInfoNavItemList, listState, Modifier.align(Alignment.BottomCenter), hazeState = hazeState, isVisible = (isNaviBarVisible), offSet = PAGE_HEADER_HEIGHT)
            }
        }
    }
}





