package io.github.lexadiky.akore.alice

@JvmInline
value class Qualifier(val tag: String) {

    companion object {

        val DEFAULT = Qualifier("__default__")
        val UNKNOWN = Qualifier("__unknown__")
    }
}
