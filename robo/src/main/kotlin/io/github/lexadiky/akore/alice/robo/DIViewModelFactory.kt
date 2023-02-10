package io.github.lexadiky.akore.alice.robo

import androidx.lifecycle.ViewModel

@PublishedApi
internal fun interface DIViewModelFactory<T: ViewModel> {

    fun create(): T
}
