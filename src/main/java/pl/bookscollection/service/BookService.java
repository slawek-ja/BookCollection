package pl.bookscollection.service;

import com.sun.xml.internal.bind.v2.TODO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  private BookRepository repository;

  @Autowired
  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  List<Book> getAllBooks() throws ServiceOperationException {
    try {
      List<Book> result = new ArrayList<>();
      repository.findAll().forEach(result::add);
      return result;
    } catch (Exception e) {
      throw new ServiceOperationException("Error occurred during getting all books", e);
    }
  }

  Optional<Book> findBookById(long bookId) throws ServiceOperationException {
    try {
      return repository.findById(bookId);
    } catch (Exception e) {
      throw new ServiceOperationException("Error occurred during getting single book by id", e);
    }
  }

  Book saveBook(@NonNull Book book) throws ServiceOperationException {
    try {
      return repository.save(book);
    } catch (Exception e) {
      throw new ServiceOperationException("Error occurred during adding new book", e);
    }
  }

  //Todo: tests for DB, we must know that DB can update Book. Waiting for task with tests
}
