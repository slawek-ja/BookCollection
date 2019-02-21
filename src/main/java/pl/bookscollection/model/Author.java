package pl.bookscollection.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Author {

  private String name;
  private String lastName;

  public Author(@NonNull String name, @NonNull String lastName) {
    this.name = name;
    this.lastName = lastName;
  }
}
