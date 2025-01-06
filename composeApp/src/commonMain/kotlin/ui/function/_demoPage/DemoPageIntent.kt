package ui.function._demoPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import moe.tlaster.precompose.navigation.BackStackEntry
import type.Character
import type.Relic

data class DemoPageState(
    val hazeState: HazeState = HazeState()
)

sealed class DemoPageIntent {
    data object Initialize : DemoPageIntent()
    data object RefreshData : DemoPageIntent()
    data class SetHazeState(val hazeState: HazeState) : DemoPageIntent()
}