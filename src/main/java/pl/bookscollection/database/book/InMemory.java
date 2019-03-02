package pl.bookscollection.database.book;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import pl.bookscollection.database.Database;
import pl.bookscollection.database.DatabaseOperationException;
import pl.bookscollection.model.Book;

public class InMemory implements Database {

  private List<Book> database = Collections.synchronizedList(new ArrayList<>());
  private long id = 0;

  @Override
  public Book save(@NonNull Book book) throws DatabaseOperationException {
    if (existById(book.getId())) {
      return updateBook(book);
    } else {
      book.setId(getUpdatedId());
      database.add(book);
      return book;
    }
  }

  @Override
  public Book findById(long id) {
    if (existById(id)) {
      return database.stream()
              .filter(book -> book.getId() == id)
              .findFirst().get();
    } else {
      return null;
    }
  }

  @Override
  public List<Book> findAll() {
    return database;
  }

  @Override
  public boolean existById(long id) {
    return database.stream().anyMatch(book -> book.getId() == id);
  }

  @Override
  public long count() {
    return database.size();
  }

  @Override
  public void deleteById(long id) throws DatabaseOperationException {
    if (database.removeIf(book -> book.getId() == id)) {
      return;
    } else {
      throw new DatabaseOperationException(String.format("There was no Book in database with id %d", id));
    }
  }

  @Override
  public void deleteAll() {
    database.clear();
  }

  private Book updateBook(Book book) throws DatabaseOperationException {
    deleteById(book.getId());
    database.add(book);
    return book;
  }

  private long getUpdatedId() {
    return ++id;
  }
}
