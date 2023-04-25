package io.github.stefankoppier.builder.dsl.processor

import com.squareup.kotlinpoet.ClassName

data class GeneratorContext(
    val originalClassName: ClassName,
    val className: ClassName,
)
