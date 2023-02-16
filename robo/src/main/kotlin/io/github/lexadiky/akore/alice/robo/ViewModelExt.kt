package io.github.lexadiky.akore.alice.robo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import io.github.lexadiky.akore.alice.DIContainer
import io.github.lexadiky.akore.alice.ModuleBuilder
import io.github.lexadiky.akore.alice.Qualifier
import kotlin.reflect.KClass
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

inline fun <reified T : ViewModel> ModuleBuilder.singleViewModel(
    crossinline provider: ModuleBuilder.DIScope.(ModuleBuilder.DIParametersHolder) -> T,
) {
    single(
        qualifier = Qualifier(T::class.qualifiedName!!),
        type = DIViewModelFactory::class,
        provider = {
            val scope = this
            DIViewModelFactory { parameters ->
                scope.provider(parameters)
            }
        }
    )
}

@Composable
inline fun <reified T : ViewModel> DIContainer.viewModel(
    vararg parameters: Any,
): T {
    val genKey = parameters.contentToString()
    val actualKey = makeActualKeyFor(T::class, genKey)

    val internalViewModelFactory = lookup(
        qualifier = Qualifier(T::class.qualifiedName!!),
        type = DIViewModelFactory::class
    )

    val params = remember(T::class.qualifiedName, *parameters) {
        ModuleBuilder.DIParametersHolder(parametersOf(*parameters))
    }

    return androidx.lifecycle.viewmodel.compose.viewModel(
        key = actualKey,
        initializer = { internalViewModelFactory.create(params) }
    ) as T
}

@PublishedApi
internal fun makeActualKeyFor(cls: KClass<*>, key: String) = "${cls.qualifiedName}:$key"