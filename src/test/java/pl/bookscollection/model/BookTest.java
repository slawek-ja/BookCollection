package pl.bookscollection.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BookTest {

  @Test
  void shouldInvokeClass() {
    //given
    Author author = new Author("Steve", "Fox");
    Cover cover = Cover.HARD;
    Book book;

    //when
    book = new Book("Green Woods", cover, author);

    //then
    assertNotNull(book);
  }

  @Test
  void shouldThrowNullPointerExceptionWhenTitleIsNull() {
    assertThrows(NullPointerException.class, () -> new Book(null, Cover.HARD, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenCoverIsNull() {
    assertThrows(NullPointerException.class, () -> new Book("Green Woods", null, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenAuthorIsNull() {
    assertThrows(NullPointerException.class, () -> new Book("Green Woods", Cover.SOFT, null));
  }
}
