package pl.bookscollection.database;

import java.util.List;

import org.springframework.data.repository.Repository;
import pl.bookscollection.model.Book;

public interface Database extends Repository<Book, Long> {

  Book save(Book book) throws DatabaseOperationException;

  Book findById(long id);

  List<Book> findAll();

  boolean existById(long id);

  long count();

  void deleteById(long id) throws DatabaseOperationException;

  void deleteAll();
}
