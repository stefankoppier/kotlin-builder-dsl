package io.github.stefankoppier.builder.dsl.processor

fun <T : Any> resolve(value: T?) =
    requireNotNull(value) { "Unable to resolve $value." }
