package studio.forface.headsdown

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.AppSettings
import studio.forface.headsdown.model.AppWithSettings
import studio.forface.headsdown.model.with
import studio.forface.headsdown.ui.HeadsDownTheme
import studio.forface.headsdown.ui.Strings
import studio.forface.headsdown.viewmodel.AppState
import studio.forface.headsdown.viewmodel.AppViewModel
import studio.forface.headsdown.viewmodel.AppsWithSettingsState

class MainActivity : AppCompatActivity() {

    private val viewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBody(
                viewModel.state,
                viewModel::addToBlockHeadsUp,
                viewModel::removeFromBlockHeadsUp
            )
        }
    }
}

@Composable
fun AppBody(
    appStateFlow: StateFlow<AppState>,
    onAddToBlockHeadsUp: (App) -> Unit,
    onRemoveFromBlockHeadsUp: (App) -> Unit
) {
    val state by appStateFlow.collectAsState()
    var isAtTop by remember { mutableStateOf(true) }

    HeadsDownTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = { Toolbar(isAtTop) }
        ) {
            SettingsPage(
                state = state,
                onAddToBlockHeadsUp = onAddToBlockHeadsUp,
                onRemoveFromBlockHeadsUp = onRemoveFromBlockHeadsUp,
                onScroll = { isAtTop = it }
            )
        }
    }
}

@Composable
fun Toolbar(isAtTop: Boolean) {
    val (elevation, backgroundColor) =
        if (isAtTop) 0.dp to MaterialTheme.colors.background
        else 8.dp to MaterialTheme.colors.surface
    TopAppBar(
        backgroundColor = animate(target = backgroundColor),
        elevation = animate(target = elevation)
    ) {
        Column(
            Modifier.align(Alignment.CenterVertically).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Strings.AppName,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5,
            )
            Text(
                text = Strings.BlockHeadsUpForSelectedAppsMessage,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }
    }
}

@Composable
fun SettingsPage(
    state: AppState,
    onAddToBlockHeadsUp: (App) -> Unit,
    onRemoveFromBlockHeadsUp: (App) -> Unit,
    onScroll: (isAtTop: Boolean) -> Unit
) {
    when (val appsState = state.appsWithSettingsState) {
        AppsWithSettingsState.Loading -> Loading()
        is AppsWithSettingsState.Data -> AppsWithSettingsList(
            data = appsState.appsWithSettings,
            onAddToBlockHeadsUp = onAddToBlockHeadsUp,
            onRemoveFromBlockHeadsUp = onRemoveFromBlockHeadsUp,
            onScroll = onScroll
        )
    }
}

@Composable
fun Loading() {
    // TODO
}

@Composable
fun AppsWithSettingsList(
    data: List<AppWithSettings>,
    onAddToBlockHeadsUp: (App) -> Unit,
    onRemoveFromBlockHeadsUp: (App) -> Unit,
    onScroll: (isAtTop: Boolean) -> Unit
) {
    val listState: LazyListState = rememberLazyListState()
    onScroll(listState.firstVisibleItemScrollOffset <= 0)
    val iconPixelSize = remember { (58 * Resources.getSystem().displayMetrics.density).toInt() }
    LazyColumnFor(
        items = data,
        state = listState,
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ) { appWithSettings ->
        AppWithSettingsItem(
            appWithSettings = appWithSettings,
            iconPixelSize = iconPixelSize,
            onAddToBlockHeadsUp = { onAddToBlockHeadsUp(appWithSettings.app) },
            onRemoveFromBlockHeadsUp = { onRemoveFromBlockHeadsUp(appWithSettings.app) })
    }
}

@Composable
fun AppWithSettingsItem(
    appWithSettings: AppWithSettings,
    iconPixelSize: Int,
    onAddToBlockHeadsUp: () -> Unit,
    onRemoveFromBlockHeadsUp: () -> Unit
) {
    val checked = appWithSettings.settings.shouldBlockHeadsUp
    val onCheckChange = { _: Boolean ->
        if (checked) onRemoveFromBlockHeadsUp() else onAddToBlockHeadsUp()
    }
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(appWithSettings.app.icon.toBitmap(iconPixelSize, iconPixelSize).asImageBitmap())
            Spacer(Modifier.width(16.dp))
            Column {
                Text(appWithSettings.app.appName, style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(4.dp))
                val packageNameStyle = MaterialTheme.typography.subtitle2
                Text(
                    appWithSettings.app.packageName.value,
                    style = packageNameStyle.copy(color = packageNameStyle.color.copy(alpha = 0.5f))
                )
            }
        }
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    operator fun <T> List<T>.times(count: Int) = toMutableList().apply {
        repeat(count -1) {
            addAll(this)
        }
    }
    val appsWithSettings = listOf(
        CineScout with AppSettings(shouldBlockHeadsUp = false),
        HeadsDown with AppSettings(shouldBlockHeadsUp = false),
        WildRift with AppSettings(shouldBlockHeadsUp = true),
        Zooba with AppSettings(shouldBlockHeadsUp = true),
    ) * 4
    val state = AppState(
        generalHeadsUpBlockEnabled = true,
        AppsWithSettingsState.Data(appsWithSettings)
    )
    val appStateFlow = MutableStateFlow(state)

    AppBody(appStateFlow, {}, {})
}
