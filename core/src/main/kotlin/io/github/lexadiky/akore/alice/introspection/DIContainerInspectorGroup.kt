package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass

class DIContainerInspectorGroup internal constructor() : DIContainerInspector {

    private val subInspectors: MutableList<DIContainerInspector> = ArrayList()

    fun register(subInspector: DIContainerInspector) {
        subInspectors += subInspector
    }

    override fun onModuleRegistered(module: DIModule) {
        subInspectors.forEach { it.onModuleRegistered(module) }
    }

    override fun onLookup(type: KClass<*>, qualifier: Qualifier, vararg parameters: Any) {
        subInspectors.forEach { it.onLookup(type, qualifier, parameters = parameters) }
    }
}