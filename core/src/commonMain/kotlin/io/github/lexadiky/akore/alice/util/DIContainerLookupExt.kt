@file:Suppress("PackageDirectoryMismatch")

package io.github.lexadiky.akore.alice

inline fun <reified T : Any> DIContainer.lookup(): T {
    return lookup(
        qualifier = Qualifier.DEFAULT,
        parameters = emptyArray()
    )
}

inline fun <reified T : Any> DIContainer.lookup(vararg parameters: Any): T {
    return lookup(
        qualifier = Qualifier.DEFAULT,
        parameters = parameters
    )
}

inline fun <reified T : Any> DIContainer.lookup(qualifier: Qualifier, vararg parameters: Any): T  {
    return lookup(
        type = T::class,
        qualifier = qualifier,
        parameters = parameters
    )
}