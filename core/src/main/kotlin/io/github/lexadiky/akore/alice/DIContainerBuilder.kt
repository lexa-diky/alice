package io.github.lexadiky.akore.alice

import io.github.lexadiky.akore.alice.introspection.DIContainerInspector
import io.github.lexadiky.akore.alice.introspection.DIContainerInspectorGroup

fun DIContainer.Companion.builder(): DIContainerBuilder = DIContainerBuilder()

class DIContainerBuilder internal constructor() {

    private val initialModules: MutableList<DIModule> = ArrayList()
    private val groupInspector: DIContainerInspectorGroup = DIContainerInspectorGroup()

    fun modules(vararg modules: DIModule) = apply {
        initialModules.addAll(modules)
    }

    fun inspector(inspector: DIContainerInspector) = apply {
        groupInspector.register(inspector)
    }

    fun build(): DIContainer {
        return DIContainer(
            inspector = groupInspector
        ).apply {
            register(modules = initialModules.toTypedArray())
        }
    }
}