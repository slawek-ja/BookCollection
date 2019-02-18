package bookscollection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    @Test
    void shouldReturnValidString() {
        //given
        String expected = "Hello World";

        //when
        String result = HelloWorld.returnHelloWorld();

        //then
        assertEquals(expected, result);
    }
}
