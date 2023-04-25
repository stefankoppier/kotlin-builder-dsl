import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SimpleClassTest {

    @Test
    fun test() {
        val fixture = SimpleClass {
            string { constant("test") }
            byte { constant(Byte.MAX_VALUE) }
            short { constant(Short.MAX_VALUE) }
            int { constant(Int.MAX_VALUE) }
            long { constant(Long.MAX_VALUE) }
            char { constant('a') }
            boolean { constant(true) }
        }

        assertThat(fixture.string).isEqualTo("test")
        assertThat(fixture.byte).isEqualTo(Byte.MAX_VALUE)
        assertThat(fixture.short).isEqualTo(Short.MAX_VALUE)
        assertThat(fixture.int).isEqualTo(Int.MAX_VALUE)
        assertThat(fixture.long).isEqualTo(Long.MAX_VALUE)
        assertThat(fixture.byte).isEqualTo(Byte.MAX_VALUE)
        assertThat(fixture.char).isEqualTo('a')
        assertThat(fixture.boolean).isEqualTo(true)
    }
}