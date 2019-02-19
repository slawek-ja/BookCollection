package bookscollection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
