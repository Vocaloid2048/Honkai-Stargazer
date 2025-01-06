package ui.function._demoPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.Navigator

class DemoPageViewModel(private val navigator: Navigator) : ViewModel() {
    private val _state = MutableStateFlow(DemoPageState())
    val state: StateFlow<DemoPageState> get() = _state

    fun handleIntent(intent: DemoPageIntent) {
        when (intent) {
            is DemoPageIntent.Initialize -> initialize()
            is DemoPageIntent.RefreshData -> refreshData()
            is DemoPageIntent.SetHazeState -> setHazeState(intent.hazeState)
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            // Initialization logic here
            //val showPopup = !Preferences().AppSettings.isLangInitialized()
            //_state.value.showPopup.value = showPopup
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
}