package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaultSizeConfig

/**
 * 按钮配置
 */
class FlexButtonConfig internal constructor(
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
	var iconInterval: Dp,
) {
	
	internal companion object : FlexDefaultSizeConfig<FlexButtonConfig> {
		
		/**
		 * 默认超小尺寸按钮配置
		 */
		override val DefaultExtraSmall: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 24.dp,
				horizontalPadding = 8.dp,
				borderWidth = 1.dp,
				fontSize = 12.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified,
				iconSize = 24.dp * 0.66f,
				iconInterval = 4.dp
			)
		
		/**
		 * 默认小尺寸按钮配置
		 */
		override val DefaultSmall: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 32.dp,
				horizontalPadding = 10.dp,
				borderWidth = 1.5.dp,
				fontSize = 16.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified,
				iconSize = 32.dp * 0.66f,
				iconInterval = 6.dp
			)
		
		/**
		 * 默认中尺寸按钮配置
		 */
		override val DefaultMedium: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 40.dp,
				horizontalPadding = 12.dp,
				borderWidth = 1.5.dp,
				fontSize = 20.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified,
				iconSize = 40.dp * 0.66f,
				iconInterval = 8.dp
			)
		
		/**
		 * 默认大尺寸按钮配置
		 */
		override val DefaultLarge: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 48.dp,
				horizontalPadding = 14.dp,
				borderWidth = 1.5.dp,
				fontSize = 24.sp,
				fontWeight = FontWeight.SemiBold,
				letterSpacing = TextUnit.Unspecified,
				iconSize = 48.dp * 0.66f,
				iconInterval = 10.dp
			)
		
		/**
		 * 默认大尺寸按钮配置
		 */
		override val DefaultExtraLarge: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 56.dp,
				horizontalPadding = 16.dp,
				borderWidth = 2.dp,
				fontSize = 28.sp,
				fontWeight = FontWeight.SemiBold,
				letterSpacing = TextUnit.Unspecified,
				iconSize = 56.dp * 0.66f,
				iconInterval = 12.dp
			)
	}
}