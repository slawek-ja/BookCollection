package pl.bookscollection.database.book;

import pl.bookscollection.database.Database;
import pl.bookscollection.database.DatabaseOperationException;
import pl.bookscollection.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemory implements Database {

  private List<Book> database = Collections.synchronizedList(new ArrayList<>());

  @Override
  public Book save(Book book) throws DatabaseOperationException {
    try {
      database.add(book);
      return book;
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }

  @Override
  public Book update(Book book) throws DatabaseOperationException {
    try {
      if (database.contains(book)) {
        database.remove(book);
        database.add(book);
        return book;
      } else {
        database.add(book);
        return book;
      }
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }

  @Override
  public List<Book> findAll() throws DatabaseOperationException {
    try {
      return database;
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }

  @Override
  public long count() throws DatabaseOperationException {
    try {
      return database.size();
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }

  @Override
  public void delete(Book book) throws DatabaseOperationException {
    try {
      database.remove(book);
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }

  @Override
  public void deleteAll() throws DatabaseOperationException {
    try {
      database.clear();
    } catch (Exception e) {
      throw new DatabaseOperationException(e.getMessage());
    }
  }
}
