package io.github.lexadiky.akore.alice

fun DIContainer.Companion.builder(): DIContainerBuilder = DIContainerBuilder()

class DIContainerBuilder internal constructor() {

    private val initialModules: MutableList<DIModule> = ArrayList()

    fun modules(vararg modules: DIModule) = apply {
        initialModules.addAll(modules)
    }

    fun build(): DIContainer {
        return DIContainer().apply {
            register(modules = initialModules.toTypedArray())
        }
    }
}