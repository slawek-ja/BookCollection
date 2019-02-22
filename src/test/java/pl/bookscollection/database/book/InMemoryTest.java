package pl.bookscollection.database.book;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bookscollection.database.Database;
import pl.bookscollection.database.DatabaseOperationException;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class InMemoryTest {

  private Database database;

  @BeforeEach
  void createDataBase() {
    database = new InMemory();
  }

  @Test
  void shouldSaveBooks() throws DatabaseOperationException {
    //given
    Book book = new Book("True story", Cover.HARD, new Author("Arthur", "Mock"));
    List<Book> expected = new ArrayList<>(Collections.singletonList(book));

    //when
    database.save(book);

    //then
    assertEquals(database.findAll(), expected);
  }

  @Test
  void shouldUpdateBook() throws DatabaseOperationException {
    //given
    Book book = new Book("True story", Cover.HARD, new Author("Arthur", "Mock"));
    Book bookUpdate = new Book("False story", Cover.HARD, new Author("Arthur", "Mock"));
    List<Book> expected = new ArrayList<>(Collections.singletonList(bookUpdate));

    //when
    database.save(book);
//    database.update() needed Id -> Waiting for #8 task
  }

}
