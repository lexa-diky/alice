package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.introspection.entity.DIContainerInspection

class DIContainerInspector {

    fun inspect(container: DIContainer) : DIContainerInspection {
        return DIContainerInspection(container.registeredModules.values.toList())
    }
}
