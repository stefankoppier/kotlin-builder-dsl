package com.github.stefankoppier.builder.dsl

infix fun <E> MutableList<E>.with(element: E): MutableList<E> {
    this.add(element)
    return this
}
