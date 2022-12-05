package com.github.stefankoppier.builder.dsl

import java.util.*
import kotlin.collections.ArrayList

class ListBuilderDsl <E, B : BuilderDsl<E>> (private val factory: B)
    : BuilderDsl<List<E>> {

    private var min: Int = 0

    private var max: Int = 10

    private var previous: (B.(E) -> B)? = null

    override fun invoke(): List<E> {
        val result = LinkedList((min..max).map { factory() })
        if (previous != null) {
            val values = result
                .mapIndexed { i, _ -> previous?.let { factory.it(result[i - 1])() } }
            result.addFirst(values[0])
            return values.map { it!! }
        }
        return result
    }

    fun between(min: Int, max: Int): ListBuilderDsl<E, B> {
        require(min >= 0) { "min must be positive, instead '$min' was given"}
        require(max >= 0) { "max must be positive, instead '$max' was given"}
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Int): ListBuilderDsl<E, B> {
        this.min = min
        return this
    }

    fun max(max: Int): ListBuilderDsl<E, B> {
        this.max = max
        return this
    }

    fun previous(transform: B.(E) -> B): ListBuilderDsl<E, B> {
        this.previous = transform
        return this
    }
}