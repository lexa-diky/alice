package io.github.lexadiky.akore.alice.util

import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.DIModule

fun DIContainer.register(modules: Array<out DIModule>) {
    modules.forEach { register(it) }
}

fun DIContainer.deregister(modules: Array<out DIModule>) {
    modules.forEach { deregister(it) }
}
