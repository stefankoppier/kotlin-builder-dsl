import io.github.stefankoppier.builder.dsl.annotations.DataGenerator

@DataGenerator
class SimpleClass(
    val name: String,
    val age: Int,
    val enum: SimpleEnum,
)
