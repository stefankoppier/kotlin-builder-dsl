import io.github.stefankoppier.builder.dsl.annotations.DataGenerator

@DataGenerator
class SimpleClass(
    val string: String,
    val byte: Byte,
    val short: Short,
    val int: Int,
    val long: Long,
    val boolean: Boolean,
    val char: Char,
)
