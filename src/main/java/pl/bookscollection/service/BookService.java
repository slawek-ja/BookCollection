package pl.bookscollection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bookscollection.database.BookRepository;
import pl.bookscollection.model.Book;

@Service
public class BookService {

  private BookRepository repository;

  @Autowired
  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public List<Book> getAllBooks() throws ServiceOperationException {
    try {
      List<Book> result = new ArrayList<>();
      repository.findAll().forEach(result::add);
      return result;
    } catch (Exception e) {
      throw new ServiceOperationException("Error occurred during getting all books", e);
    }
  }

  public Optional<Book> getBook(long bookId) throws ServiceOperationException {
    try {
      return repository.findById(bookId);
    } catch (Exception e) {
      throw new ServiceOperationException(String.format("Error occurred during getting single book by id. Book id: %d", bookId), e);
    }
  }

  public Book addBook(@NonNull Book book) throws ServiceOperationException {
    try {
      return repository.save(book);
    } catch (Exception e) {
      throw new ServiceOperationException("Error occurred during adding new book", e);
    }
  }

  public void deleteAllBooks() throws ServiceOperationException {
    try {
      repository.deleteAll();
    } catch (Exception e) {
      throw new ServiceOperationException("An error occurred during deleting all books", e);
    }
  }

  public void deleteBook(long bookId) throws ServiceOperationException {
    try {
      repository.deleteById(bookId);
    } catch (Exception e) {
      throw new ServiceOperationException(String.format("An error occurred during deleting book by id. Book id: %d", bookId), e);
    }
  }
}
