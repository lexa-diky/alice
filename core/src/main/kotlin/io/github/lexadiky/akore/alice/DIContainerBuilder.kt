package io.github.lexadiky.akore.alice

import io.github.lexadiky.akore.alice.introspection.DIContainerEventListener
import io.github.lexadiky.akore.alice.introspection.DIContainerEventListenerGroup
import io.github.lexadiky.akore.alice.util.register

fun DIContainer.Companion.builder(): DIContainerBuilder = DIContainerBuilder()

class DIContainerBuilder internal constructor() {

    private var wasBuiltOnce: Boolean = false

    private val initialModules: MutableList<DIModule> = ArrayList()
    private val groupEventInspector: DIContainerEventListenerGroup = DIContainerEventListenerGroup()

    fun modules(vararg modules: DIModule) = apply {
        assertIsBuildable()
        initialModules.addAll(modules)
    }

    fun inspector(inspector: DIContainerEventListener) = apply {
        assertIsBuildable()
        groupEventInspector.register(inspector)
    }

    fun build(): DIContainer {
        assertIsBuildable()
        wasBuiltOnce = true

        return DIContainer(eventListener = groupEventInspector).apply {
            register(modules = initialModules.toTypedArray())
            groupEventInspector.onContainerBuild(this)
        }
    }

    private fun assertIsBuildable() {
        assert(!wasBuiltOnce) { "container could only be build once with single builder" }
    }
}
