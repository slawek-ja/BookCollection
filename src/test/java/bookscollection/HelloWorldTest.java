package bookscollection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class HelloWorldTest {

  @Test
  void shouldInvokeClass() {
    //given
    HelloWorld helloWorld;

    //when
    helloWorld = new HelloWorld();

    //then
    assertNotNull(helloWorld);
  }

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
