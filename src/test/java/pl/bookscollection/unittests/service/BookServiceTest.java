package pl.bookscollection.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.NonTransientDataAccessException;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Book;
import pl.bookscollection.service.BookService;
import pl.bookscollection.service.ServiceOperationException;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class BookServiceTest {

  @Autowired
  @Mock
  private BookRepository repository;

  private BookService service;

  @BeforeEach
  void setUp() {
    service = new BookService(repository);
  }

  @Test
  void shouldGetAllBooks() throws Exception {
    //given
    List<Book> expected = Arrays.asList(BookGenerator.getBook(), BookGenerator.getBook());
    when(repository.findAll()).thenReturn(expected);

    //when
    List<Book> result = service.getAllBooks();

    //then
    assertEquals(expected, result);
    verify(repository).findAll();
  }

  @Test
  void shouldGetOneBook() throws Exception {
    //given
    Book expected = BookGenerator.getBook();
    when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

    //when
    Optional<Book> result = service.getBook(expected.getId());

    //then
    assertEquals(expected, result.get());
    verify(repository).findById(expected.getId());
  }

  @Test
  void shouldReturnEmptyOptionalIfBookDoesNotExistInDatabase() throws Exception {
    //given
    long id = 10;
    when(repository.findById(id)).thenReturn(Optional.empty());

    //when
    Optional<Book> result = service.getBook(id);

    //then
    assertEquals(Optional.empty(), result);
    verify(repository).findById(id);
  }

  @Test
  void shouldAddBook() throws Exception {
    //given
    Book expected = BookGenerator.getBook();
    when(repository.save(expected)).thenReturn(expected);
    when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

    //when
    service.addBook(expected);
    Optional<Book> result = service.getBook(expected.getId());

    //then
    assertEquals(expected, result.get());
    verify(repository).save(expected);
  }

  @Test
  void shouldThrowExceptionWhenBookIsNull() {
    assertThrows(NullPointerException.class, () -> service.addBook(null));
  }

  @Test
  void shouldDeleteAllBooks() throws Exception {
    //given
    doNothing().when(repository).deleteAll();

    //when
    service.deleteAllBooks();

    //then
    verify(repository).deleteAll();
  }

  @Test
  void shouldDeleteBook() throws Exception {
    //given
    long id = 10;
    doNothing().when(repository).deleteById(id);

    //when
    service.deleteBook(id);

    //then
    verify(repository).deleteById(id);
  }

  @Test
  void shouldThrowExceptionWhenGettingAllBooksWentWrong() {
    //given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);

    //when
    doThrow(mockedException).when(repository).findAll();

    //then
    assertThrows(ServiceOperationException.class, () -> service.getAllBooks());
  }

  @Test
  void shouldThrowExceptionWhenGettingOneBookWentWrong() {
    //given
    long id = 10;
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);

    //when
    doThrow(mockedException).when(repository).findById(id);

    //then
    assertThrows(ServiceOperationException.class, () -> service.getBook(id));
  }

  @Test
  void shouldThrowExceptionWhenAddingBookWentWrong() {
    //given
    Book book = BookGenerator.getBook();
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);

    //when
    doThrow(mockedException).when(repository).save(book);

    //then
    assertThrows(ServiceOperationException.class, () -> service.addBook(book));
  }

  @Test
  void shouldThrowExceptionWhenDeletingAllBooksWentWrong() {
    //given
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);

    //when
    doThrow(mockedException).when(repository).deleteAll();

    //then
    assertThrows(ServiceOperationException.class, () -> service.deleteAllBooks());
  }

  @Test
  void shouldThrowExceptionWhenDeletingOneBookWentWrong() {
    //given
    long id = 10;
    NonTransientDataAccessException mockedException = Mockito.mock(NonTransientDataAccessException.class);

    //when
    doThrow(mockedException).when(repository).deleteById(id);

    //then
    assertThrows(ServiceOperationException.class, () -> service.deleteBook(id));
  }
}
