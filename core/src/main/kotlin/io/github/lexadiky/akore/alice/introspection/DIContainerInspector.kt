package io.github.lexadiky.akore.alice.introspection

import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.DIContainerImpl
import io.github.lexadiky.akore.alice.introspection.entity.DIContainerInspection

class DIContainerInspector {

    fun inspect(container: DIContainer) : DIContainerInspection {
        require(container is DIContainerImpl) {
            "default $this con only inspect descendants of ${DIContainerImpl::class}"
        }
        return DIContainerInspection(container.registeredModules.values.toList())
    }
}
