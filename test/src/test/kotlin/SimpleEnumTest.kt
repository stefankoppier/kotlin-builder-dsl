import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleEnumTest {

    @Test
    fun test() {
        val fixture = SimpleEnum {
            filter { it != SimpleEnum.FEMALE }
        }

        assertEquals(fixture, SimpleEnum.MALE)
    }
}