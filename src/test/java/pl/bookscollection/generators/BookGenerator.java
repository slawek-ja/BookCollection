package pl.bookscollection.generators;

import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;

public class BookGenerator {
  public static Book getBook() {
    return new Book("Green woods", Cover.HARD, new Author("Steve", "Fox"));
  }

  public static Book getBookWithSpecifiedId(long id) {
    Book book = getBook();
    book.setId(id);
    return book;
  }

  public static Book getBookWithSpecifiedAuthor(Author author) {
    Book book = getBook();
    book.setAuthor(author);
    return book;
  }
}
