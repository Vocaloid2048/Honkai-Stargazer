package ui.function.lightconeInfoPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import type.Lightcone
import type.Path
import utils.app.Language

class LightconeInfoPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(LightconeInfoPageState())
    val state: StateFlow<LightconeInfoPageState> get() = _state

    fun handleIntent(intent: LightconeInfoPageIntent) {
        when (intent) {
            is LightconeInfoPageIntent.Initialize -> initialize()
            is LightconeInfoPageIntent.RefreshData -> refreshData()
            is LightconeInfoPageIntent.SetHazeState -> setHazeState(intent.hazeState)
            is LightconeInfoPageIntent.SetBackStackEntry -> setBackStateEntry(intent.backStackEntry)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
            val lightconeFileName = _state.value.backStackEntry?.query<String>("fileName")!!

            if (lightconeFileName.isEmpty()) return@launch

            _state.value.lcInfoJson.value = Lightcone.getLightconeDataFromJSON(lightconeFileName, Language.TextLanguageInstance)
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            // Refresh data, grab from API, etc.

        }
    }
    private fun setHazeState(hazeState: HazeState) {
        _state.value = _state.value.copy(hazeState = hazeState)
    }
    private fun setBackStateEntry(backStackEntry: BackStackEntry) {
        _state.value = _state.value.copy(backStackEntry = backStackEntry)
    }
}