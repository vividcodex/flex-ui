package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("flexUiSample") {
			FlexTheme {
				App()
			}
        }
    }
}