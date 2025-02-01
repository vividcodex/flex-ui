package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexInputConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*

/**
 * FlexInput 输入框
 *
 * @param value 输入框的当前值
 * @param onValueChange 当输入值发生变化时调用的回调函数
 * @param modifier 应用于输入框的修饰符
 * @param sizeType 输入框的尺寸类型。
 * @param colorType 输入框的颜色类型。
 * @param cornerType 输入框的圆角类型。
 * @param enabled 是否启用输入框，默认为 `true`
 * @param readOnly 是否为只读模式，默认为 `false`。
 * @param maxLength 允许输入的最大字符数，默认为 `Int.MAX_VALUE`
 * @param inputRegex 限制输入内容的正则表达式，为 `null` 时不限制
 * @param placeholder 输入框的占位内容
 * @param leadingIcon 输入框前置图标
 * @param trailingIcon 输入框后置图标
 * @param prefix 输入框前缀组件
 * @param suffix 输入框后缀组件
 * @param keyboardOptions 键盘输入选项，控制键盘类型、行为等
 * @param keyboardActions 键盘操作选项，定义输入法操作按钮的行为
 * @param visualTransformation 视觉转换，控制输入文本的显示效果
 */
@Composable
fun FlexInput(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexInputs.DefaultSizeType,
	colorType: FlexColorType = FlexInputs.DefaultColorType,
	cornerType: FlexCornerType = FlexInputs.DefaultCornerType,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	maxLength: Int = Int.MAX_VALUE,
	inputRegex: Regex? = null,
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: FlexInputIcon? = null,
	trailingIcon: FlexInputIcon? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	val current = LocalFlexConfig.current
	val config = current.input.getConfig(sizeType)
	val targetColor = current.theme.colorScheme.current.getColor(colorType)
	
	CompositionLocalProvider(
		LocalTextSelectionColors provides TextSelectionColors(
			handleColor = targetColor,
			backgroundColor = targetColor.copy(alpha = 0.15f)
		)
	) {
		val leadingIconInteractionSource = remember { MutableInteractionSource() }
		val trailingIconInteractionSource = remember { MutableInteractionSource() }
		val textFieldIsFocused by interactionSource.collectIsFocusedAsState()
		val leadingIconIsFocused by leadingIconInteractionSource.collectIsFocusedAsState()
		val trailingIconIsFocused by trailingIconInteractionSource.collectIsFocusedAsState()
		val isFocused by remember(textFieldIsFocused, leadingIconIsFocused, trailingIconIsFocused) {
			derivedStateOf { textFieldIsFocused || leadingIconIsFocused || trailingIconIsFocused }
		}
		
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val color by animateColorAsState(
			targetValue = if (enabled) targetColor else targetColor.copy(alpha = 0.6f)
		)
		val textStyle by remember(fontSize, config.fontWeight, letterSpacing, color) {
			derivedStateOf {
				TextStyle(
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
					color = color
				)
			}
		}
		
		val focusRequester = remember { FocusRequester() }
		
		BasicTextField(
			value = value,
			onValueChange = {
				if (it.length <= maxLength && inputRegex?.matches(it) != false) {
					onValueChange(it)
				}
			},
			modifier = Modifier
				.focusRequester(focusRequester),
			enabled = enabled,
			readOnly = readOnly,
			textStyle = textStyle,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = true,
			visualTransformation = visualTransformation,
			interactionSource = interactionSource,
			cursorBrush = SolidColor(color),
			decorationBox = @Composable { innerTextField ->
				FlexInputDecorationBox(
					value = value,
					modifier = modifier,
					cornerType = cornerType,
					innerTextField = innerTextField,
					config = config,
					textStyle = textStyle,
					isFocused = isFocused,
					color = color,
					targetColor = targetColor,
					placeholder = placeholder,
					leadingIcon = leadingIcon,
					trailingIcon = trailingIcon,
					prefix = prefix,
					suffix = suffix,
					focusRequester = focusRequester,
					leadingIconInteractionSource = leadingIconInteractionSource,
					trailingIconInteractionSource = trailingIconInteractionSource,
				)
			}
		)
	}
}

@Composable
private fun FlexInputDecorationBox(
	value: String,
	modifier: Modifier,
	cornerType: FlexCornerType,
	innerTextField: @Composable () -> Unit,
	config: FlexInputConfig,
	textStyle: TextStyle,
	isFocused: Boolean,
	color: Color,
	targetColor: Color,
	placeholder: @Composable (() -> Unit)?,
	leadingIcon: FlexInputIcon?,
	trailingIcon: FlexInputIcon?,
	prefix: @Composable (() -> Unit)?,
	suffix: @Composable (() -> Unit)?,
	focusRequester: FocusRequester,
	leadingIconInteractionSource: MutableInteractionSource,
	trailingIconInteractionSource: MutableInteractionSource,
) {
	val borderWidth by animateDpAsState(config.borderWidth)
	val minWidth by animateDpAsState(config.minWidth)
	val height by animateDpAsState(config.height)
	val corner = height * cornerType.percent
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val borderColor by animateColorAsState(
		targetValue = if (isFocused) targetColor else targetColor.copy(alpha = 0f),
	)
	val backgroundColor by animateColorAsState(
		targetValue = if (isFocused) Color.Gray.copy(alpha = 0f) else Color.Gray.copy(alpha = 0.15f),
	)
	val interval by animateDpAsState(config.horizontalPadding / 2)
	Row(
		modifier = Modifier
			.widthIn(min = minWidth)
			.height(height)
			.border(
				width = borderWidth,
				color = borderColor,
				shape = cornerShape
			)
			.background(
				color = backgroundColor,
				shape = cornerShape
			)
			.then(modifier)
			.padding(horizontal = interval + borderWidth),
		verticalAlignment = Alignment.CenterVertically,
	) {
		val iconSize by animateDpAsState(config.iconSize)
		if (leadingIcon != null) {
			FlexInputIcon(
				icon = leadingIcon,
				iconSize = iconSize,
				targetColor = targetColor,
				isFocused = isFocused,
				focusRequester = focusRequester,
				interactionSource = leadingIconInteractionSource
			)
		}
		if (prefix != null) {
			Spacer(modifier = Modifier.width(interval))
			CompositionLocalProvider(
				LocalContentColor provides color,
				LocalTextStyle provides textStyle
			) {
				prefix()
			}
		}
		Spacer(modifier = Modifier.width(interval))
		
		Box {
			val isEmpty by remember(value) {
				derivedStateOf { value.isEmpty() }
			}
			if (placeholder != null && isEmpty) {
				CompositionLocalProvider(
					LocalTextStyle provides textStyle.copy(
						color = color.copy(alpha = 0.6f)
					)
				) {
					placeholder()
				}
			}
			innerTextField()
		}
		
		Spacer(modifier = Modifier.width(interval))
		if (suffix != null) {
			CompositionLocalProvider(
				LocalContentColor provides color,
				LocalTextStyle provides textStyle
			) {
				suffix()
			}
			Spacer(modifier = Modifier.width(interval))
		}
		if (trailingIcon != null) {
			FlexInputIcon(
				icon = trailingIcon,
				iconSize = iconSize,
				targetColor = targetColor,
				isFocused = isFocused,
				focusRequester = focusRequester,
				interactionSource = trailingIconInteractionSource
			)
		}
	}
}

@Composable
private fun FlexInputIcon(
	icon: FlexInputIcon,
	iconSize: Dp,
	targetColor: Color,
	isFocused: Boolean,
	focusRequester: FocusRequester,
	interactionSource: MutableInteractionSource,
) {
	val isPressed by interactionSource.collectIsPressedAsState()
	val tint = if (icon.tint != Color.Unspecified) icon.tint else targetColor
	val iconTint by animateColorAsState(
		targetValue = when {
			!isFocused -> tint.copy(alpha = 0.6f)
			isPressed -> tint.copy(alpha = 0.8f)
			else -> tint
		}
	)
	val size by animateDpAsState(
		targetValue = if (icon.size != Dp.Unspecified) icon.size else iconSize,
	)
	val rotateDegrees by animateFloatAsState(icon.rotateDegrees)
	Icon(
		imageVector = icon.icon,
		contentDescription = null,
		modifier = Modifier
			.size(size)
			.rotate(rotateDegrees)
			.pointerHoverIcon(PointerIcon.Default)
			.then(
				if (icon.onClick == null) Modifier else Modifier.clickable(
					interactionSource = interactionSource,
					indication = null,
					onClick = {
						icon.onClick()
						focusRequester.requestFocus()
					}
				)
			),
		tint = iconTint
	)
}

object FlexInputs {
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = getDefaultSizeType { input.sizeType }
	
	val DefaultColorType: FlexColorType
		@Composable
		get() = getDefaultColorType { input.colorType }
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = getDefaultCornerType { input.cornerType }
	
	fun icon(
		icon: ImageVector,
		tint: Color = Color.Unspecified,
		rotate: Float = 0f,
		size: Dp = Dp.Unspecified,
		onClick: (() -> Unit)? = null,
	) = FlexInputIcon(icon, tint, rotate, size, onClick)
}

class FlexInputIcon internal constructor(
	val icon: ImageVector,
	val tint: Color,
	val rotateDegrees: Float,
	val size: Dp,
	val onClick: (() -> Unit)?,
)