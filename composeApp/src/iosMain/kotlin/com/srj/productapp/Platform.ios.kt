package com.srj.productapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun getScreenWidth(): Dp {
    return LocalWindowInfo.current.containerSize.width.dp
}

@Composable
actual fun getScreenHeight(): Dp {
    return LocalWindowInfo.current.containerSize.height.dp
}