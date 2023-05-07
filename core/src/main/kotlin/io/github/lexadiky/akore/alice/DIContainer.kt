@file:OptIn(AliceInternalApi::class)

package io.github.lexadiky.akore.alice

import io.github.lexadiky.akore.alice.introspection.DIContainerEventListener
import kotlin.reflect.KClass
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication

interface DIContainer {

    fun register(module: DIModule)

    fun deregister(module: DIModule)

    fun <T : Any> lookup(type: KClass<T>, qualifier: Qualifier, vararg parameters: Any): T

    companion object
}

internal class DIContainerImpl internal constructor(
    private val application: KoinApplication = koinApplication {},
    private val eventListener: DIContainerEventListener,
) : DIContainer {

    internal var registeredModules: HashMap<String, DIModule> = HashMap()

    override fun register(module: DIModule) {
        if (module.name in registeredModules) {
            return
        }

        registeredModules[module.name] = module
        application.modules(module.koinModule)
        eventListener.onModuleRegistered(module)
    }

    override fun deregister(module: DIModule) {
        registeredModules.remove(module.name)
        application.koin.unloadModules(listOf(module.koinModule))
        eventListener.onModuleDeregistered(module)
    }

    override fun <T : Any> lookup(
        type: KClass<T>,
        qualifier: Qualifier,
        vararg parameters: Any,
    ): T {
        eventListener.onLookup(
            type = type,
            qualifier = qualifier,
            parameters = parameters
        )
        return application.koin.get(clazz = type, qualifier = named(qualifier.tag)) {
            parametersOf(*parameters)
        }
    }

    companion object
}
