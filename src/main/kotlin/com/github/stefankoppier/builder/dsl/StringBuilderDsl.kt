package com.github.stefankoppier.builder.dsl

import com.github.curiousoddman.rgxgen.RgxGen
import org.slf4j.LoggerFactory

class StringBuilderDsl : BuilderDsl<String> {

    private val logger = LoggerFactory.getLogger(StringBuilderDsl::class.java)

    private var constant: String? = null

    private var format: String? = null

    private var min = 0

    private var max = 24

    override fun invoke(): String {
        if (constant != null) {
            return constant!!
        }
        if (format != null) {
            try {
                return RgxGen(format).generate()
            } catch (e: Exception) {
                logger.error("Failed to generate regex from '$format' falling back to a random value.", e)
            }
        }
        return Faker().nextString(min, max)
    }

    fun constant(value: String): StringBuilderDsl {
        constant = value
        format = null
        return this
    }

    fun format(pattern: String): StringBuilderDsl {
        format = pattern
        constant = null
        return this
    }

    fun between(min: Int, max: Int): StringBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given"}
        require(max >= 0) { "max must be positive, instead '$max' was given"}
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Int): StringBuilderDsl {
        this.min = min
        return this
    }

    fun max(max: Int): StringBuilderDsl {
        this.max = max
        return this
    }
}

fun String.Companion.of(transform: StringBuilderDsl.() -> StringBuilderDsl = { StringBuilderDsl() }): String {
    return transform(StringBuilderDsl())()
}