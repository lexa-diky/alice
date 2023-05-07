package io.github.lexadiky.akore.alice.test

import io.github.lexadiky.akore.alice.AliceInternalApi
import io.github.lexadiky.akore.alice.DIModule
import kotlin.reflect.KClass
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

@OptIn(AliceInternalApi::class, KoinExperimentalAPI::class)
fun DIModule.isFullyDefined(extraTypes: List<KClass<*>> = emptyList()) = koinModule.verify(
    extraTypes = extraTypes
)
