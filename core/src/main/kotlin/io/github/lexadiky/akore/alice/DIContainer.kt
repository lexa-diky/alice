package io.github.lexadiky.akore.alice

import io.github.lexadiky.akore.alice.introspection.DIContainerInspector
import kotlin.reflect.KClass
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication

class DIContainer internal constructor(
    private val application: KoinApplication = koinApplication { },
    private val inspector: DIContainerInspector
) {
    private var registeredModules: HashMap<String, DIModule> = HashMap()

    fun register(modules: Array<out DIModule>) {
        val newModules = modules.filter { module -> module.name !in registeredModules }
        newModules.forEach { module ->
            registeredModules[module.name] = module
            inspector.onModuleRegistered(module)
        }
        application.modules(newModules.map { it.koinModule })
    }

    fun <T : Any> lookup(type: KClass<T>, qualifier: Qualifier, vararg parameters: Any): T {
        inspector.onLookup(
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
