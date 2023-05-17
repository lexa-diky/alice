@file:OptIn(AliceInternalApi::class)

package io.github.lexadiky.akore.alice.introspection.entity

import io.github.lexadiky.akore.alice.AliceInternalApi
import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.introspection.DIModuleInspector
import org.koin.core.annotation.KoinInternalApi

interface DIModuleInspection {

    val uniqueId: String
    val module: DIModule
    val bindings: List<DIModuleBindingInspection>
    val recursiveBindings: List<DIModuleBindingInspection>
    val importedModules: List<DIModuleInspection>
    val recursiveImportedModules: List<DIModuleInspection>
}

@OptIn(KoinInternalApi::class)
internal class LazyDIModuleInspection(
    private val inspector: DIModuleInspector,
    override val module: DIModule,
) : DIModuleInspection {

    override val uniqueId: String = module.koinModule.id

    override val bindings: List<DIModuleBindingInspection> by lazy {
        module.koinModule.mappings.asBinding()
            .distinct()
    }

    override val recursiveBindings: List<DIModuleBindingInspection> by lazy {
        inspector.collectMappingsRecursively(module).asBinding()
            .distinct()
    }

    override val importedModules: List<DIModuleInspection> by lazy {
        module.importedModules.map { inspector.inspect(it) }
            .distinct()
    }

    override val recursiveImportedModules: List<DIModuleInspection> by lazy {
        inspector.collectImportedModulesRecursively(module)
            .map { inspector.inspect(it) }.distinct()
    }

    override fun equals(other: Any?): Boolean {
        return this.module == (other as? DIModuleInspection)?.module
    }

    override fun hashCode(): Int {
        return this.module.hashCode()
    }

    override fun toString(): String {
        return "DIModuleInspection(module=${module.name})"
    }
}
