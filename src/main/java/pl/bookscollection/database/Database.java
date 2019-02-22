package pl.bookscollection.database;

import pl.bookscollection.model.Book;

import java.util.List;

public interface Database {

  Book save(Book book) throws DatabaseOperationException;

  Book update(Book book) throws DatabaseOperationException;

  List<Book> findAll() throws DatabaseOperationException;

  long count() throws DatabaseOperationException;

  void delete(Book book) throws DatabaseOperationException;

  void deleteAll() throws DatabaseOperationException;
}
