package com.github.stefankoppier.builder.dsl.primitives

import com.github.curiousoddman.rgxgen.RgxGen
import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker
import org.slf4j.LoggerFactory

class StringBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<String> {

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
                val message = "Failed to generate regex from '$format' falling back to a random value."
                logger.error(message, e)
            }
        }
        return faker.nextString(min, max)
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
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Int): StringBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    fun max(max: Int): StringBuilderDsl {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

fun String.Companion.of(transform: StringBuilderDsl.() -> StringBuilderDsl = { StringBuilderDsl() }): String {
    return transform(StringBuilderDsl())()
}
