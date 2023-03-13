package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.introspection.entity.DIModuleInspection
import io.github.lexadiky.akore.alice.introspection.entity.LazyDIModuleInspection
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.IndexKey
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module

internal typealias KoinMapping = Map<IndexKey, InstanceFactory<*>>

@OptIn(KoinInternalApi::class)
class DIModuleInspector {

    private val deepRecursiveMappingCollector = DeepRecursiveFunction<Module, KoinMapping> { module ->
        val innerMappings = module.includedModules.map { callRecursive(it) }
            .fold(emptyMap<IndexKey, InstanceFactory<*>>()) { a, b -> a + b }

        module.mappings + innerMappings
    }

    private val deepRecursiveImportedModules = DeepRecursiveFunction<DIModule, List<DIModule>> { module ->
        module.importedModules + module + module.importedModules.flatMap { callRecursive(it) }
    }

    fun inspect(module: DIModule) : DIModuleInspection =
        LazyDIModuleInspection(this, module)

    internal fun collectMappingsRecursively(module: DIModule): Map<IndexKey, InstanceFactory<*>> =
        deepRecursiveMappingCollector.invoke(module.koinModule)

    internal fun collectImportedModulesRecursively(module: DIModule): List<DIModule> =
        deepRecursiveImportedModules.invoke(module)
}