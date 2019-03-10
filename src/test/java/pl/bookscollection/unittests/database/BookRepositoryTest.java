package pl.bookscollection.unittests.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Book;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class BookRepositoryTest {

  @Autowired
  @Mock
  private BookRepository repository;

  @Test
  void shouldSaveBook() {
    //given
    Book expected = BookGenerator.getBook();
    when(repository.save(expected)).thenReturn(expected);

    //when
    Book result = repository.save(expected);

    //then
    assertEquals(expected, result);
    verify(repository).save(expected);
  }

  @Test
  void shouldFindAllBooksInDatabase() {
    //given
    Book firstBook = BookGenerator.getBook();
    Book secondBook = BookGenerator.getBook();
    List<Book> expected = Arrays.asList(firstBook, secondBook);
    when(repository.findAll()).thenReturn(expected);

    //when
    List<Book> result = new ArrayList<>();
    repository.findAll().forEach(result::add);

    //then
    assertEquals(expected, result);
    verify(repository).findAll();
  }

  @Test
  void shouldFindBookWithSpecifiedId() {
    //given
    Book expected = BookGenerator.getBook();
    when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

    //when
    Optional<Book> result = repository.findById(expected.getId());

    //then
    assertEquals(expected, result.get());
    verify(repository).findById(expected.getId());
  }

  @Test
  void shouldReturnTrueWhenBookExistById() {
    //given
    Book expected = BookGenerator.getBook();
    when(repository.existsById(expected.getId())).thenReturn(true);

    //when
    boolean result = repository.existsById(expected.getId());

    //then
    assertTrue(result);
    verify(repository).existsById(expected.getId());
  }

  @Test
  void shouldReturnFalseWhenBookNotExistById() {
    //given
    long id = 1;
    when(repository.existsById(id)).thenReturn(false);

    //when
    boolean result = repository.existsById(id);

    //then
    assertFalse(result);
    verify(repository).existsById(id);
  }

  @Test
  void shouldCountBooksInDatabase() {
    //given
    long expected = 5;
    when(repository.count()).thenReturn(expected);

    //when
    long result = repository.count();

    //then
    assertEquals(expected, result);
    verify(repository).count();
  }

  @Test
  void shouldDeleteBookById() {
    //given
    long id = 1;
    doNothing().when(repository).deleteById(id);

    //when
    repository.deleteById(id);

    //then
    verify(repository).deleteById(id);
  }

  @Test
  void shouldDeleteAllBooks() {
    //given
    doNothing().when(repository).deleteAll();

    //when
    repository.deleteAll();

    //then
    verify(repository).deleteAll();
  }
}
