package pl.bookscollection.integrationtests.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.generators.AuthorGenerator;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;

@DataJpaTest
@Rollback
class BookRepositoryTest {

  @Autowired
  private BookRepository repository;

  @Test
  void shouldSaveBook() {
    //given
    Book expected = BookGenerator.getBook();

    //when
    Book result = repository.save(expected);
    expected.setId(result.getId());
    expected.getAuthor().setId(result.getAuthor().getId());

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldUpdateBook() {
    //given
    Book bookToUpdate = repository.save(BookGenerator.getBookWithSpecifiedTitle("Some title"));

    //when
    bookToUpdate.setTitle("Other title");
    repository.save(bookToUpdate);
    Optional<Book> result = repository.findById(bookToUpdate.getId());

    //then
    assertEquals(bookToUpdate, result.get());
  }

  @Test
  void shouldUpdateAuthor() {
    //given
    Author authorToUpdate = AuthorGenerator.getAuthorWithSpecifiedName("John", "Fox");
    Book book = repository.save(BookGenerator.getBookWithSpecifiedAuthor(authorToUpdate));

    //when
    book.getAuthor().setName("Steve");
    book.getAuthor().setLastName("Rush");
    repository.save(book);
    Optional<Book> result = repository.findById(book.getId());

    //then
    assertEquals(book.getAuthor(), result.get().getAuthor());
  }

  @Test
  void shouldFindAllBooksInDatabase() {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    Book addedFirstBook = repository.save(firstBook);
    Book addedSecondBook = repository.save(secondBook);
    List<Book> expected = Arrays.asList(addedFirstBook, addedSecondBook);

    //when
    List<Book> result = new ArrayList<>();
    repository.findAll().forEach(result::add);

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldFindBookById() {
    //given
    Book someBook = BookGenerator.getBook();
    Book expected = repository.save(BookGenerator.getBook());
    repository.save(someBook);

    //when
    Optional<Book> result = repository.findById(expected.getId());

    //then
    assertEquals(expected, result.get());
  }

  @Test
  void shouldReturnTrueWhenBookExistById() {
    //given
    Book book = BookGenerator.getBook();
    long id = repository.save(book).getId();

    //when
    boolean result = repository.existsById(id);

    //then
    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenBookNotExistById() {
    //given
    long id = 1;

    //when
    boolean result = repository.existsById(id);

    //then
    assertFalse(result);
  }

  @Test
  void shouldCountBooksInDataBase() {
    //given
    Book bookFirst = BookGenerator.getBook();
    Book bookSecond = BookGenerator.getBook();
    Book bookThird = BookGenerator.getBook();
    repository.saveAll(Arrays.asList(bookFirst, bookSecond, bookThird));
    long expected = 3;

    //when
    long result = repository.count();

    //then
    assertEquals(expected, result);
  }

  @Test
  void shouldDeleteBookById() {
    //given
    Book bookToDelete = BookGenerator.getBook();
    long id = repository.save(bookToDelete).getId();

    //when
    repository.deleteById(id);
    boolean result = repository.existsById(id);

    //then
    assertFalse(result);
  }

  @Test
  void shouldDeleteAllBooks() {
    //given
    Book bookFirst = BookGenerator.getBook();
    Book bookSecond = BookGenerator.getBook();
    Book bookThird = BookGenerator.getBook();
    repository.saveAll(Arrays.asList(bookFirst, bookSecond, bookThird));
    long expectedSize = 0;

    //when
    repository.deleteAll();
    long result = repository.count();

    //then
    assertEquals(expectedSize, result);
  }

  @Test
  void shouldPerformDeleteAllBooksMethodEvenIfDatabaseIsEmpty() {
    //given
    long expectedSize = 0;

    //when
    repository.deleteAll();
    long result = repository.count();

    //then
    assertEquals(expectedSize, result);
  }
}
