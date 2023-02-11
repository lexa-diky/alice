package io.github.lexadiky.akore.alice.robo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import io.github.lexadiky.akore.alice.DIModule
import io.github.lexadiky.akore.alice.ModuleBuilder

@Composable
fun eagerModule(name: String, vararg keys: Any, definition: ModuleBuilder.() -> Unit): DIModule =
    remember(keys = keys) { io.github.lexadiky.akore.alice.eagerModule(name, definition) }

inline fun <reified T: ViewModel> ModuleBuilder.singleViewModel(crossinline provider: ModuleBuilder.DIScope.(ModuleBuilder.DIParametersHolder) -> T) {
    integrityChecker.check(cls = T::class, allowInternal = inInternalScope)

    module.single(definition = { params ->
        DIViewModelFactory {
            val diParametersHolder = ModuleBuilder.DIParametersHolder(params)
            ModuleBuilder.DIScope(this).provider(diParametersHolder)
        }
    })
}

