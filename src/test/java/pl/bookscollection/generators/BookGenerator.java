package pl.bookscollection.generators;

import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;

public class BookGenerator {
  public static Book getBook() {
    return new Book("Green woods", Cover.HARD, new Author("Steve", "Fox"));
  }

  public static Book getBookWithSpecifiedId(long id) {
    return new Book(id, "Green woods", Cover.HARD, new Author("Steve", "Fox"));
  }

  public static Book getBookWithSpecifiedAuthor(Author author) {
    return new Book(1, "Green woods", Cover.HARD, author);
  }
}
