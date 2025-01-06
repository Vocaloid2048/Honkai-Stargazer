package ui.function._demoPage

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.Navigator
import ui.components.HeaderData
import ui.components.defaultHeaderData

@Composable
fun DemoPage(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    headerData: HeaderData = defaultHeaderData,
)  {
    val viewModel = DemoPageViewModel(navigator = navigator)
    val state by viewModel.state.collectAsState()

    viewModel.handleIntent(DemoPageIntent.Initialize)

    Box{

    }
}