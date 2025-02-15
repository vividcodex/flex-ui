package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.radio.*
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexButtonPage() {
	var buttonText by remember { mutableStateOf("FlexButton") }
	var buttonType by remember { mutableStateOf(FlexButtonType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var iconType by remember { mutableStateOf(IconType.None) }
	var iconPosition by remember { mutableStateOf(FlexButtonIconPosition.End) }
	var scaleEffect by remember { mutableStateOf(false) }
	var enabled by remember { mutableStateOf(true) }
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.weight(1f)
			.padding(12.dp),
	) {
		Box(
			modifier = Modifier
				.weight(1f)
				.fillMaxHeight(),
			contentAlignment = Alignment.Center
		) {
			FlexButton(
				text = buttonText,
				icon = iconType.icon,
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				buttonType = buttonType,
				iconPosition = iconPosition,
				iconRotation = 0f,
				scaleEffect = scaleEffect,
				enabled = enabled
			)
		}
		Spacer(Modifier.width(12.dp))
		val code = """
			FlexButton(
				text = "$buttonText",
				icon = ${iconType.code},
				sizeType = FlexSizeType.$sizeType,
				colorType = FlexColorType.$colorType,
				cornerType = FlexCornerType.$cornerType,
				buttonType = FlexButtonType.$buttonType,
				iconPosition = FlexButtonIconPosition.$iconPosition,
				iconRotation = 0f,
				scaleEffect = $scaleEffect,
				enabled = $enabled
			)
		""".trimIndent()
		Code(code)
	}
	HorizontalDivider()
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.height(252.dp)
			.padding(4.dp)
			.verticalScroll(verticalScrollState)
			.padding(8.dp)
	) {
		TitleLayout(
			title = "Button Text"
		) {
			FlexInput(
				value = buttonText,
				onValueChange = { buttonText = it },
				modifier = Modifier.width(220.dp),
				sizeType = FlexSizeType.Small,
				colorType = colorType,
				placeholder = { Text("Button Text") }
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Button Type"
		) {
			FlexRadio(
				selectedKey = buttonType,
				onSelectedKeyChange = { buttonType = it },
				options = FlexButtonType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Size Type"
		) {
			FlexRadio(
				selectedKey = sizeType,
				onSelectedKeyChange = { sizeType = it },
				options = FlexSizeType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Color Type"
		) {
			FlexRadio(
				selectedKey = colorType,
				onSelectedKeyChange = { colorType = it },
				options = FlexColorType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				colorType = colorType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Corner Type"
		) {
			FlexRadio(
				selectedKey = cornerType,
				onSelectedKeyChange = { cornerType = it },
				options = FlexCornerType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				cornerType = cornerType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Icon"
		) {
			FlexRadio(
				selectedKey = iconType,
				onSelectedKeyChange = { iconType = it },
				options = IconType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
			FontWeight
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Icon Position"
		) {
			FlexRadio(
				selectedKey = iconPosition,
				onSelectedKeyChange = { iconPosition = it },
				options = FlexButtonIconPosition.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Scale Effect"
		) {
			FlexSwitch(
				checked = scaleEffect,
				onCheckedChange = { scaleEffect = it },
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Enabled"
		) {
			FlexSwitch(
				checked = enabled,
				onCheckedChange = { enabled = it },
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary
			)
		}
	}
}

private enum class IconType(
	val icon: ImageVector?,
	val code: String,
) {
	
	Search(
		icon = Icons.Rounded.Search,
		code = "Icons.Rounded.Search"
	),
	
	Add(
		icon = Icons.Rounded.Add,
		code = "Icons.Rounded.Add"
	),
	
	KeyboardArrowRight(
		icon = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
		code = "Icons.AutoMirrored.Rounded.KeyboardArrowRight"
	),
	
	None(
		icon = null,
		code = "null"
	)
}