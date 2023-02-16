package io.github.lexadiky.akore.alice.robo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.lookup

@Composable
inline fun <reified T : Any> DIContainer.inject(): T = remember { lookup() }

@Composable
inline fun <reified T : Any> DIContainer.inject(vararg parameters: Any): T = remember(*parameters) {
    lookup(*parameters)
}

private val LocalDIContainer = compositionLocalOf<DIContainer> {
    error("no di container attached")
}

@Composable
fun DIApplication(container: DIContainer, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDIContainer provides container) {
        content()
    }
}

@Composable
fun DIFeature(vararg includes: DIModule, content: @Composable () -> Unit) {
    val diContainer = LocalDIContainer.current
    diContainer.register(includes)
    content()
}

val di: DIContainer
    @Composable get() = LocalDIContainer.current