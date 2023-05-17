package io.github.lexadiky.akore.alice.introspection.entity

import io.github.lexadiky.akore.alice.Qualifier
import io.github.lexadiky.akore.alice.introspection.KoinMapping
import kotlin.reflect.KClass
import org.koin.core.instance.InstanceFactory

data class DIModuleBindingInspection(
    val qualifier: Qualifier,
    val type: KClass<*>
)

internal fun KoinMapping.asBinding(): List<DIModuleBindingInspection> = map { (_, factory) ->
    factory.asBinding()
}

internal fun InstanceFactory<*>.asBinding(): DIModuleBindingInspection {
    val def = this.beanDefinition

    return DIModuleBindingInspection(
        qualifier = def.qualifier?.let { Qualifier(it.value) }
            ?: Qualifier.UNKNOWN,
        type = def.primaryType
    )
}