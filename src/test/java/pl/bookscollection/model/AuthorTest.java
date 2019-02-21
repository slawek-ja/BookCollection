package pl.bookscollection.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AuthorTest {

  @Test
  void shouldInvokeClass() {
    //given
    Author author;

    //when
    author = new Author("Tim", "Restwood");

    //then
    assertNotNull(author);
  }

  @Test
  void shouldThrowNullPointerExceptionWhenNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Author(null, "Restwood"));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenLastNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Author("Tim", null));
  }
}