package io.github.stefankoppier.builder.dsl.annotations

@Target(AnnotationTarget.CLASS)
annotation class DataGenerator(val name: String = "", val postfix: String = "Generator")
