package io.github.lexadiky.akore.alice

import kotlin.reflect.KClass
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.Kind
import org.koin.core.instance.SingleInstanceFactory
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

class DIModule(val name: String, internal val koinModule: Module)

@OptIn(KoinInternalApi::class)
class ModuleBuilder(private val module: Module) {
    @PublishedApi
    internal var inInternalScope: Boolean = false

    fun import(other: DIModule) {
        if (inInternalScope) {
            throw IllegalStateException("imports in internal scope are not supported")
        }
        module.includes(other.koinModule)
    }

    fun <T : Any> single(
        type: KClass<T>,
        qualifier: Qualifier = Qualifier.DEFAULT,
        provider: DIScope.(DIParametersHolder) -> T,
    ) {
        val bean = BeanDefinition(
            scopeQualifier = named("_root_"),
            primaryType = type,
            qualifier = named(qualifier.tag),
            definition = { params ->
                val diParametersHolder = DIParametersHolder(params)
                DIScope(this).provider(diParametersHolder)
            },
            kind = Kind.Singleton,
            secondaryTypes = emptyList()
        )
        val factory = SingleInstanceFactory(bean)
        module.indexPrimaryType(factory)
    }

    inline fun internal(scope: ModuleBuilder.() -> Unit) {
        inInternalScope = true
        scope()
        inInternalScope = false
    }

    fun build(name: String): DIModule = DIModule(name, module)

    @JvmInline
    value class DIScope(@PublishedApi internal val scope: Scope) {

        inline fun <reified T : Any> inject(
            qualifier: Qualifier = Qualifier.DEFAULT,
            vararg parameters: Any,
        ) = scope.get<T>(
            qualifier = named(qualifier.tag),
            parameters = { parametersOf(*parameters) }
        )
    }

    @JvmInline
    value class DIParametersHolder(@PublishedApi internal val parameters: ParametersHolder) {

        inline fun <reified T : Any> get(): T = parameters.get()
    }
}

fun module(name: String, definition: ModuleBuilder.() -> Unit): Lazy<DIModule> = lazy {
    ModuleBuilder(module { }).apply(definition).build(name)
}

fun eagerModule(name: String, definition: ModuleBuilder.() -> Unit): DIModule =
    ModuleBuilder(module { }).apply(definition).build(name)
