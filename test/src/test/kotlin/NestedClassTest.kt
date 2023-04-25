import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class NestedClassTest {

    @Test
    @Disabled("TODO: Deal with nested type")
    fun test() {
        val fixture = nestedClass { constant(NestedClass(null)) }
    }
}
