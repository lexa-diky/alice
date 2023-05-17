package io.github.lexadiky.akore.alice.util

import io.github.lexadiky.akore.alice.ModuleBuilder

inline fun <reified T: Any> ModuleBuilder.single(
    noinline provider: ModuleBuilder.DIScope.(ModuleBuilder.DIParametersHolder) -> T
) {
    single(
        type = T::class,
        provider = provider
    )
}
