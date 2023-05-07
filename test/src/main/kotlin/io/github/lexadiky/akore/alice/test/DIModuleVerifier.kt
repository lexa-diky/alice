package io.github.lexadiky.akore.alice.test

import io.github.lexadiky.akore.alice.AliceInternalApi
import io.github.lexadiky.akore.alice.DIModule
import kotlin.reflect.KClass
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.MissingKoinDefinitionException
import org.koin.test.verify.verify

fun DIModule.isFullyDefined(extraTypes: List<KClass<*>> = emptyList(), rethrow: Boolean = false): Boolean = try {
    shouldBeFullyDefined(extraTypes)
    true
} catch (e: MissingKoinDefinitionException) {
    if (rethrow) {
        throw e
    }
    false
}

@OptIn(AliceInternalApi::class, KoinExperimentalAPI::class)
fun DIModule.shouldBeFullyDefined(extraTypes: List<KClass<*>> = emptyList()) {
    koinModule.verify(
        extraTypes = extraTypes
    )
}
