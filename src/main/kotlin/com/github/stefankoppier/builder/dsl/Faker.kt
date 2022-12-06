package com.github.stefankoppier.builder.dsl

import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random

class Faker(private val random: Random = Random.Default) {
    fun nextInt(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int {
        return if (min == max) min else random.nextInt(min, max)
    }

    fun nextLong(min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE): Long {
        return if (min == max) min else random.nextLong(min, max)
    }

    fun nextBoolean(): Boolean {
        return random.nextBoolean()
    }

    fun nextFloat(): Float {
        return random.nextFloat()
    }

    fun nextDouble(): Double {
        return random.nextDouble()
    }

    fun nextChar(): Char {
        val pool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return pool[nextInt(0, pool.size)]
    }

    fun nextString(min: Int = 0, max: Int = 24): String {
        require(min >= 0) { "min must be positive, instead '$min' was given" }
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return (0..nextInt(min, max)).map { nextChar() }.joinToString(separator = "")
    }

    inline fun <reified E : Enum<E>> nextEnum(predicate: (E) -> Boolean = { true }): E {
        val options = enumValues<E>().filter(predicate)

        return options[nextInt(0, options.size)]
    }

    fun nextLocalDate(): LocalDate {
        val min = java.time.LocalDate.of(1970, 1, 1).toEpochDay()
        val max = java.time.LocalDate.of(2050, 12, 31).toEpochDay()
        return LocalDate.ofEpochDay(nextLong(min, max))
    }

    fun nextLocalTime(): LocalTime {
        return LocalTime.of(nextInt(0, 23), nextInt(0, 59), nextInt(0, 59))
    }

    fun nextOffsetDateTime(): OffsetDateTime {
        return OffsetDateTime.of(nextLocalDate(), nextLocalTime(), ZoneOffset.UTC)
    }
}
