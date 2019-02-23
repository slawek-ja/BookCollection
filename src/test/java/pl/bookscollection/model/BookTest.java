package pl.bookscollection.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    book = new Book(1, "Green Woods", cover, author);

    //then
    assertNotNull(book);
  }

  @Test
  void shouldGetValidValuesFromClass() {
    //given
    long expectedId = 1;
    String expectedTitle = "Green Woods";
    Cover expectedCover = Cover.SOFT;
    Author expectedAuthor = new Author("Steve", "Fox");
    Book book = new Book(expectedId, expectedTitle, expectedCover, expectedAuthor);

    //when
    long idResult = book.getId();
    String titleResult = book.getTitle();
    Cover coverResult = book.getCover();
    final Author authorResult = book.getAuthor();


    //then
    assertEquals(expectedId, idResult);
    assertEquals(expectedTitle, titleResult);
    assertEquals(expectedCover, coverResult);
    assertEquals(expectedAuthor, authorResult);
  }

  @Test
  void shouldThrowNullPointerExceptionWhenTitleIsNull() {
    assertThrows(NullPointerException.class, () -> new Book(1, null, Cover.HARD, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenCoverIsNull() {
    assertThrows(NullPointerException.class, () -> new Book(1, "Green Woods", null, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenAuthorIsNull() {
    assertThrows(NullPointerException.class, () -> new Book(1, "Green Woods", Cover.SOFT, null));
  }
}
