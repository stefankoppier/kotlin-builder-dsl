import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class SimpleEnumTest {

    @Test
    fun test() {
        val fixture = simpleEnum { filter { it != SimpleEnum.FEMALE } }

        assertEquals(fixture, SimpleEnum.MALE)
    }
}
