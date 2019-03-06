package pl.bookscollection.generators;

import pl.bookscollection.model.Author;

public class AuthorGenerator {
  public static Author getAuthor() {
    return new Author("Steve", "Fox");
  }

  public static Author getAuthorWithSpecifiedName(String name, String lastName) {
    return new Author(name, lastName);
  }

  public static Author getAuthorWithSpecifiedId(long id) {
    Author author = getAuthor();
    author.setId(id);
    return author;
  }
}
