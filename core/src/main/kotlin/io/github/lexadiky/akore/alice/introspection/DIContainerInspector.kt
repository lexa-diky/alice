package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass

interface DIContainerInspector {

    fun onModuleRegistered(module: DIModule) = Unit

    fun onLookup(type: KClass<*>, qualifier: Qualifier, vararg parameters: Any) = Unit
}
