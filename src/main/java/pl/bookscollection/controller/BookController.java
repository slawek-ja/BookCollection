package pl.bookscollection.controller;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bookscollection.model.Book;
import pl.bookscollection.service.BookService;

@RestController
@RequestMapping("/books/")
@CrossOrigin
public class BookController {

  private BookService service;

  @Autowired
  public BookController(@NonNull BookService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<?> getAllBooks() {
    try {
      return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while getting books."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{bookId}")
  public ResponseEntity<?> getBook(@PathVariable("bookId") long bookId) {
    try {
      Optional<Book> book = service.getBook(bookId);
      if (book.isPresent()) {
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new Message("Book not found with passed id."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(String.format("Internal server error while getting book by id: %d.", bookId)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<?> addBook(@RequestBody Book book) {
    try {
      Book addedBook = service.addBook(book);
      return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while adding new book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("{bookId}")
  public ResponseEntity<?> updateBook(@RequestBody Book book) {
    try {
      Optional<Book> bookFromDatabase = service.getBook(book.getId());
      if (bookFromDatabase.isPresent() && bookFromDatabase.get().getId() == book.getId()) {
        Book updatedBook = service.addBook(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
      }
      return new ResponseEntity<>(new Message("Passed data is invalid. Please verify book id."), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while updating specified book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("{bookId}")
  public ResponseEntity<?> deleteBook(@PathVariable("bookId") long bookId) {
    try {
      Optional<Book> bookToDelete = service.getBook(bookId);
      if (bookToDelete.isPresent()) {
        service.deleteBook(bookId);
        return new ResponseEntity<>(bookToDelete.get(), HttpStatus.OK);
      }
      return new ResponseEntity<>(new Message("Book not found."), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while deleting specified book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("deleteAll")
  public ResponseEntity<?> deleteAllBooks() {
    try {
      service.deleteAllBooks();
      return new ResponseEntity<>(new Message("Deleted all books."), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while deleting all books."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
