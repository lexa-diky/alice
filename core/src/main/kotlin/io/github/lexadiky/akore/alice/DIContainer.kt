package io.github.lexadiky.akore.alice

import io.github.lexadiky.akore.alice.introspection.DIContainerEventListener
import kotlin.reflect.KClass
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication

class DIContainer internal constructor(
    private val application: KoinApplication = koinApplication { },
    private val eventListener: DIContainerEventListener
) {
    private var registeredModules: HashMap<String, DIModule> = HashMap()

    fun register(module: DIModule) {
        if (module.name in registeredModules) {
            return
        }

        registeredModules[module.name] = module
        application.modules(module.koinModule)
        eventListener.onModuleRegistered(module)
    }

    fun deregister(module: DIModule) {
        registeredModules.remove(module.name)
        application.koin.unloadModules(listOf(module.koinModule))
        eventListener.onModuleDeregistered(module)
    }

    fun <T : Any> lookup(type: KClass<T>, qualifier: Qualifier, vararg parameters: Any): T {
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
