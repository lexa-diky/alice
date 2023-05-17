package io.github.lexadiky.akore.alice.util

import io.github.lexadiky.akore.alice.ModuleBuilder

inline fun <reified T: Any> ModuleBuilder.DIScope.inject(vararg parameters: Any): T {
    return inject(parameters = parameters)
}