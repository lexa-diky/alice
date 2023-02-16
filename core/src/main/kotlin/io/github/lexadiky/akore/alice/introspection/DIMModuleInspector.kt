package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass
import org.koin.core.annotation.KoinInternalApi

class DIMModuleInspector {

    fun inspect(module: DIModule) = InspectableDIModule(module)
}

@OptIn(KoinInternalApi::class)
@JvmInline
value class InspectableDIModule(private val module: DIModule) {

    val name get() = module.name

    fun definitions(): List<Definition> {
        return module.koinModule.mappings.map {
            val beanDef = it.value.beanDefinition
            Definition(
                qualifier = beanDef.qualifier?.value?.let(::Qualifier)
                    ?: Qualifier.DEFAULT,
                type = beanDef.primaryType
            )
        }
    }

    data class Definition(val qualifier: Qualifier, val type: KClass<*>)
}

