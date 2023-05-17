package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass

class DIContainerEventListenerGroup internal constructor() : DIContainerEventListener {

    private val subInspectors: MutableList<DIContainerEventListener> = ArrayList()

    fun register(subInspector: DIContainerEventListener) {
        subInspectors += subInspector
    }

    override fun onModuleRegistered(module: DIModule) {
        subInspectors.forEach { it.onModuleRegistered(module) }
    }

    override fun onModuleDeregistered(module: DIModule) {
        subInspectors.forEach { it.onModuleDeregistered(module) }
    }

    override fun onLookup(type: KClass<*>, qualifier: Qualifier, vararg parameters: Any) {
        subInspectors.forEach { it.onLookup(type, qualifier, parameters = parameters) }
    }

    override fun onContainerBuild(container: DIContainer) {
        subInspectors.forEach { it.onContainerBuild(container) }
    }
}
