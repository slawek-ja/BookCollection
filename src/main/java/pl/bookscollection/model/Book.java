package pl.bookscollection.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Book {

  private String title;
  private Cover cover;
  private Author author;

  public Book(@NonNull String title, @NonNull Cover cover, @NonNull Author author) {
    this.title = title;
    this.cover = cover;
    this.author = author;
  }
}
