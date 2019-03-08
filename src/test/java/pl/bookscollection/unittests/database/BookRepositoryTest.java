package pl.bookscollection.unittests.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Book;

import java.util.Optional;

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
  void shouldReturnNullWhenTryingToFindBookByIdAndBookNotExist() {
    //given
    long id = 1;
    when(repository.findById(id)).thenReturn(null);

    //when
    Optional<Book> result = repository.findById(id);

    //then
    assertNull(result);
    verify(repository).findById(id);
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
  void shouldCountBooksInDataBase() {
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
