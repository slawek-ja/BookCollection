package pl.bookscollection.database.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bookscollection.database.Database;
import pl.bookscollection.database.DatabaseOperationException;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;


class InMemoryTest {

  private Database database;

  @BeforeEach
  void createDataBase() {
    database = new InMemory();
  }

  @Test
  void shouldSaveBooks() throws DatabaseOperationException {
    //given
    Book book = BookGenerator.getBook();
    List<Book> expected = new ArrayList<>(Collections.singletonList(book));

    //when
    database.save(book);

    //then
    assertEquals(expected, database.findAll());
  }

  @Test
  void shouldUpdateExistingBook() throws DatabaseOperationException {
    //given
    Book book = BookGenerator.getBook();
    Author author = new Author("Anna", "Mint");
    Book expected = BookGenerator.getBookWithSpecifiedAuthor(author);
    final long expectedDatabaseSize = 1;

    //when
    database.save(book);
    expected.setId(book.getId());
    database.save(expected);
    Book result = database.findById(expected.getId());
    long resultSize = database.count();

    //then
    assertEquals(expected, result);
    assertEquals(expectedDatabaseSize, resultSize);
  }

  @Test
  void shouldFindAllBooksInDatabase() throws DatabaseOperationException {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    Book thirdBook = BookGenerator.getBook();
    database.save(firstBook);
    database.save(secondBook);
    database.save(thirdBook);
    List<Book> expected = new ArrayList<>(Arrays.asList(firstBook, secondBook, thirdBook));

    //when
    List<Book> result = database.findAll();

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldFindBookById() throws DatabaseOperationException {
    //given
    Book exampleBook = BookGenerator.getBook();
    Book expected = BookGenerator.getBook();
    database.save(exampleBook);
    database.save(expected);

    //when
    Book result = database.findById(expected.getId());

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldReturnNullWhenTryingToFindByIdNotExistingBook() {
    //when
    Book result = database.findById(10);

    //then
    assertNull(result);
  }

  @Test
  void shouldReturnValidNumberOfBooksInDatabase() throws DatabaseOperationException {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    Book thirdBook = BookGenerator.getBook();
    database.save(firstBook);
    database.save(secondBook);
    database.save(thirdBook);
    long expected = 3;

    //when
    long result = database.count();

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldDeleteBookById() throws DatabaseOperationException {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    Book thirdBook = BookGenerator.getBook();
    database.save(firstBook);
    database.save(secondBook);
    database.save(thirdBook);
    List<Book> expected = new ArrayList<>(Arrays.asList(firstBook, thirdBook));

    //when
    database.deleteById(secondBook.getId());
    List<Book> result = database.findAll();

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldDeleteAllBooksInDatabase() throws DatabaseOperationException {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    Book thirdBook = BookGenerator.getBook();
    database.save(firstBook);
    database.save(secondBook);
    database.save(thirdBook);
    long expected = 0;

    //when
    database.deleteAll();
    long result = database.count();

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldThrowDatabaseOperationExceptionWhenTryingToDeleteByIdNotExistingBook() {
    assertThrows(DatabaseOperationException.class, () -> database.deleteById(1));
  }
}
