package pl.bookscollection.unittests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;

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
  void shouldGetValidValuesFromClass() {
    //given
    String expectedTitle = "Green Woods";
    Cover expectedCover = Cover.SOFT;
    Author expectedAuthor = new Author("Steve", "Fox");
    Book book = new Book(expectedTitle, expectedCover, expectedAuthor);

    //when
    String titleResult = book.getTitle();
    Cover coverResult = book.getCover();
    final Author authorResult = book.getAuthor();


    //then
    assertEquals(expectedTitle, titleResult);
    assertEquals(expectedCover, coverResult);
    assertEquals(expectedAuthor, authorResult);
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
