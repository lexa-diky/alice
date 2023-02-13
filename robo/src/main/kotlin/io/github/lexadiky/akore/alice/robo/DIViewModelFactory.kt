package io.github.lexadiky.akore.alice.robo

import androidx.lifecycle.ViewModel
import io.github.lexadiky.akore.alice.ModuleBuilder

@PublishedApi
internal fun interface DIViewModelFactory<T: ViewModel> {

    fun create(diParametersHolder: ModuleBuilder.DIParametersHolder): T
}
