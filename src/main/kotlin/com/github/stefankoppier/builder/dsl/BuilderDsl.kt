package com.github.stefankoppier.builder.dsl

interface BuilderDsl<T> : () -> T {
    fun constant(value: T): BuilderDsl<T>
}
