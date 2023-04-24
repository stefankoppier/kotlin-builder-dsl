import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleClassTest {

    @Test
    fun test() {
        val fixture = SimpleClass {
            name { constant("test") }
        }

        assertEquals(fixture.name, "test")
    }
}