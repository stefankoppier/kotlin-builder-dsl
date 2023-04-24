package io.github.stefankoppier.builder.dsl

/**
 * Base interface for creating builders that can be used to generate values of type [T].
 *
 * @sample [io.github.stefankoppier.builder.dsl.primitives.BooleanGenerator]
 */
interface BuilderDsl<T> : () -> T
