package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass

interface DIContainerEventListener {

    fun onModuleRegistered(module: DIModule) = Unit

    fun onLookup(type: KClass<*>, qualifier: Qualifier, vararg parameters: Any) = Unit

    fun onContainerBuild(container: DIContainer) {

    }
}
