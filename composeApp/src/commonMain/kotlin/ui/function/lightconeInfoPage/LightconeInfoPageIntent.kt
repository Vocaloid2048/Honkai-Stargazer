package ui.function.lightconeInfoPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import moe.tlaster.precompose.navigation.BackStackEntry

data class LightconeInfoPageState(
    val hazeState: HazeState = HazeState(),
    val backStackEntry: BackStackEntry? = null,
    val lcInfoJson: MutableState<JsonElement> = mutableStateOf(Json.parseToJsonElement("{}")),
)

sealed class LightconeInfoPageIntent {
    data object Initialize : LightconeInfoPageIntent()
    data object RefreshData : LightconeInfoPageIntent()
    data class SetHazeState(val hazeState: HazeState) : LightconeInfoPageIntent()
    data class SetBackStackEntry(val backStackEntry: BackStackEntry) : LightconeInfoPageIntent()
}