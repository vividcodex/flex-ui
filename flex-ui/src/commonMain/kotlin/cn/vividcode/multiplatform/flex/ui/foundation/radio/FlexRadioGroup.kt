package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.isDark
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexSwitchType.Default
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexSwitchType.Swipe
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.jvm.JvmName

/**
 * FlexRadioGroup 单选框组
 *
 * @param options 选项 [RadioOption] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param switchType 切换类型
 * @param scaleEffect 缩放效果
 */
@Composable
fun <Key> FlexRadioGroup(
	options: List<RadioOption<Key>>,
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
	switchType: FlexSwitchType = FlexRadioGroups.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioGroups.DefaultScaleEffect,
) {
	val targetSelectedKey by remember(options, selectedKey) {
		derivedStateOf {
			val option = options.find { it.key == selectedKey }
			if (option?.enabled == true) option.key else {
				options.find { it.enabled }?.key?.also {
					onSelectedKeyChange(it)
				} ?: selectedKey
			}
		}
	}
	when (switchType) {
		Default -> FlexDefaultRadioGroup(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
		
		Swipe -> FlexSwipeRadioGroup(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
	}
}

@Suppress("ConstPropertyName")
object FlexRadioGroups {
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = LocalFlexConfig.current.default.sizeType
	
	val DefaultColorType: FlexColorType
		@Composable
		get() = LocalFlexConfig.current.default.colorType
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = LocalFlexConfig.current.default.cornerType
	
	val DefaultRadioType = FlexRadioType.Default
	
	val DefaultSwitchType = Default
	
	const val DefaultScaleEffect = true
}

enum class FlexRadioType {
	
	/**
	 * 按钮
	 */
	Button,
	
	/**
	 * 默认（线框）
	 */
	Default,
}

enum class FlexSwitchType {
	
	/**
	 * 默认（直接切换）
	 */
	Default,
	
	/**
	 * 滑动切换
	 */
	Swipe
}

/**
 * FlexRadioGroup 单选框组
 *
 * @param options 选项 [Pair] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@JvmName("FlexRadioGroupWithPairOptions")
@Composable
fun <Key> FlexRadioGroup(
	options: List<Pair<Key, String>>,
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
	switchType: FlexSwitchType = FlexRadioGroups.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioGroups.DefaultScaleEffect,
) {
	FlexRadioGroup(
		options = options.map { RadioOption(it.first, it.second) },
		selectedKey = selectedKey,
		onSelectedKeyChange = onSelectedKeyChange,
		sizeType = sizeType,
		colorType = colorType,
		cornerType = cornerType,
		radioType = radioType,
		switchType = switchType,
		scaleEffect = scaleEffect,
	)
}

/**
 * 选项
 */
class RadioOption<Key>(
	val key: Key,
	val value: String,
	val enabled: Boolean = true,
)

internal val BorderColor = Color.Gray.copy(alpha = 0.3f)

internal val UnselectedFontColor
	@Composable
	get() = when (LocalDarkTheme.current) {
		true -> Color.LightGray
		false -> Color.DarkGray
	}

internal val DisabledBackgroundColor = Color.Gray.copy(alpha = 0.15f)

/**
 * 单选框文本
 */
@Composable
internal fun FlexRadioText(
	value: String,
	enabled: Boolean,
	selected: Boolean,
	color: Color,
	config: FlexRadioConfig,
	radioType: FlexRadioType,
	isPressed: Boolean,
	isHovered: Boolean,
	scaleEffect: Boolean,
) {
	val targetFontColor by animateColorAsState(
		targetValue = when {
			!enabled -> UnselectedFontColor.copy(alpha = 0.8f)
			selected -> {
				when (radioType) {
					FlexRadioType.Button -> if (color.isDark) Color.White else Color.Black
					FlexRadioType.Default -> {
						when {
							isPressed -> color.brightness(0.9f)
							isHovered -> color.brightness(1.15f)
							else -> color
						}
					}
				}
			}
			
			isPressed -> color.brightness(0.9f)
			isHovered -> color
			else -> UnselectedFontColor
		}
	)
	val targetScale by remember(enabled, scaleEffect, isPressed, isHovered) {
		derivedStateOf {
			when {
				!enabled || !scaleEffect -> 1f
				isPressed -> 0.98f
				isHovered -> 1.02f
				else -> 1f
			}
		}
	}
	val scale by animateFloatAsState(
		targetValue = targetScale
	)
	Text(
		text = value,
		modifier = Modifier.scale(scale),
		color = targetFontColor,
		fontSize = config.fontSize,
		fontWeight = config.fontWeight,
		lineHeight = config.fontSize,
		letterSpacing = config.letterSpacing,
	)
}

/**
 * 单选框竖线
 */
@Composable
internal fun <Key> FlexRadioLine(
	index: Int,
	options: List<RadioOption<Key>>,
	selectedKey: Key,
	borderWidth: Dp,
) {
	val targetLineColor by animateColorAsState(
		targetValue = when {
			options[index].key != selectedKey && options[index - 1].key != selectedKey -> BorderColor
			!options[index].enabled || !options[index - 1].enabled -> DisabledBackgroundColor
			else -> Color.Transparent
		}
	)
	Spacer(
		modifier = Modifier
			.width(borderWidth)
			.fillMaxHeight()
			.padding(
				vertical = borderWidth
			)
			.background(targetLineColor)
	)
}

internal fun getCornerShape(
	cornerType: FlexCornerType,
	corner: Dp,
): Shape = when (cornerType) {
	FlexCornerType.None -> RectangleShape
	FlexCornerType.Circle -> CircleShape
	else -> RoundedCornerShape(corner)
}