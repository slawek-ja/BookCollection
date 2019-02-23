package pl.bookscollection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Book {

  private long id;
  @NonNull
  private String title;
  @NonNull
  private Cover cover;
  @NonNull
  private Author author;
}
