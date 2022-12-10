![Coverage](.github/badges/jacoco.svg)

# Kotlin Builder DSL

## Usage

### Gradle kotlin dsl
```kotlin
dependencies {
    testImplementation("com.github.stefankoppier:kotlin-builder-dsl:0.0.1")
}
```

### Examples

### Generating a random `Int`
```kotlin
Int.of()
```

### Generating a random positive `Long`
```kotlin
Long.of { min(0) }
```

### Generating a `String` that satisfies a regex
```kotlin
String.of { format("[aAzZ]+") }
```

### Generating a `List` of random size with `Int` elements between 0 and 5
```kotlin
ListBuilder(IntBuilder.between(0, 5))()
```

### Generating enums

```kotlin
class DayOfWeekEnumBuilder(faker: Faker = Faker()) : EnumBuilder<DayOfWeek> {
    override fun allValues(): Array<DayOfWeek> {
        return DayOfWeek.values()
    }
}
```

```kotlin
DayOfWeekEnumBuilder().filter { it != DayOfWeek.MONDAY }()
```

## Additional information
For more information, visit the [project documentation](https://stefankoppier.github.io/kotlin-builder-dsl/)