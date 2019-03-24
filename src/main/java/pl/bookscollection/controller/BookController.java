package pl.bookscollection.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.bookscollection.model.Book;
import pl.bookscollection.service.BookService;

@RestController
@RequestMapping("/books/")
@CrossOrigin
@Api(value = "Books", description = "Available operations for book collection application", tags = {"Books"})
public class BookController {

  private BookService service;
  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private Validator validator = factory.getValidator();

  @Autowired
  public BookController(@NonNull BookService service) {
    this.service = service;
  }

  @GetMapping
  @ApiOperation(
          value = "Get all books.",
          notes = "Get all books from database.",
          response = Book.class,
          responseContainer = "List")
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = Book.class),
          @ApiResponse(code = 500, message = "Internal server error while getting books.", response = Message.class)})
  public ResponseEntity<?> getAllBooks() {
    try {
      return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while getting books."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("{bookId}")
  @ApiOperation(
          value = "Get book.",
          notes = "Get specified books from database.",
          response = Book.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = Book.class),
          @ApiResponse(code = 404, message = "Book not found with passed id.", response = Message.class),
          @ApiResponse(code = 500, message = "Internal server error while getting book by id.", response = Message.class)})
  public ResponseEntity<?> getBook(
          @ApiParam(value = "Id of specified book to be found", required = true)
          @PathVariable("bookId") long bookId) {
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
  @ApiOperation(
          value = "Add new book.",
          notes = "Add new book to database.",
          response = Book.class)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {
          @ApiResponse(code = 201, message = "CREATED", response = Book.class),
          @ApiResponse(code = 400, message = "Passed data is invalid.", response = Message.class),
          @ApiResponse(code = 500, message = "Internal server error while adding new book.", response = Message.class)})
  public ResponseEntity<?> addBook(
          @ApiParam(value = "Body of book to add in JSON format")
          @RequestBody Book book) {
    try {
      Set<ConstraintViolation<Book>> violationsChecker = validator.validate(book);
      if (violationsChecker.size() > 0) {
        String errorMessage = violationsChecker
                .iterator()
                .next()
                .getMessage();
        return new ResponseEntity<>(new Message(String.format("Passed data is invalid. Problem: %s", errorMessage)), HttpStatus.BAD_REQUEST);
      }
      Book addedBook = service.addBook(book);
      return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while adding new book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping
  @ApiOperation(
          value = "Update book.",
          notes = "Update existing book in database.",
          response = Book.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = Book.class),
          @ApiResponse(code = 400, message = "Passed data is invalid.", response = Message.class),
          @ApiResponse(code = 500, message = "Internal server error while updating specified book.", response = Message.class)})
  public ResponseEntity<?> updateBook(
          @ApiParam(value = "Body of book to update in JSON format", required = true)
          @RequestBody Book book) {
    try {
      Set<ConstraintViolation<Book>> violationsChecker = validator.validate(book);
      if (violationsChecker.size() > 0) {
        String errorMessage = violationsChecker
                .iterator()
                .next()
                .getMessage();
        return new ResponseEntity<>(new Message(String.format("Passed data is invalid. Problem: %s", errorMessage)), HttpStatus.BAD_REQUEST);
      }
      Optional<Book> bookFromDatabase = service.getBook(book.getId());
      if (bookFromDatabase.isPresent() && bookFromDatabase.get().getId() == book.getId()) {
        Book updatedBook = service.addBook(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
      }
      return new ResponseEntity<>(new Message("Passed data is invalid."), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while updating specified book."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("{bookId}")
  @ApiOperation(
          value = "Delete book.",
          notes = "Delete specified book from database.",
          response = Book.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "OK", response = Book.class),
          @ApiResponse(code = 404, message = "Book not found.", response = Message.class),
          @ApiResponse(code = 500, message = "Internal server error while deleting specified book.", response = Message.class)})
  public ResponseEntity<?> deleteBook(
          @ApiParam(value = "Id of specified book to delete", required = true)
          @PathVariable("bookId") long bookId) {
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
  @ApiOperation(
          value = "Delete all books.",
          notes = "Delete all books from database.",
          response = Message.class)
  @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Deleted all books.", response = Message.class),
          @ApiResponse(code = 500, message = "Internal server error while deleting all books.", response = Message.class)})
  public ResponseEntity<?> deleteAllBooks() {
    try {
      service.deleteAllBooks();
      return new ResponseEntity<>(new Message("Deleted all books."), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message("Internal server error while deleting all books."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
