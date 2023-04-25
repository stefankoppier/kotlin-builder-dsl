package io.github.stefankoppier.builder.dsl.processor

import com.squareup.kotlinpoet.ClassName

fun <T : Any> resolve(value: T?) = requireNotNull(value) { "Unable to resolve $value." }

fun ClassName.camelCase() = toString().replaceFirstChar { it.lowercase() }
