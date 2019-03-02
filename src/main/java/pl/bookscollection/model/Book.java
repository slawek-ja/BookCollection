package pl.bookscollection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Book {

  private long id;
  @NonNull
  private String title;
  @NonNull
  private Cover cover;
  @NonNull
  private Author author;

  public Book(@NonNull String title, @NonNull Cover cover, @NonNull Author author) {
    this.id = -1;
    this.title = title;
    this.cover = cover;
    this.author = author;
  }
}
