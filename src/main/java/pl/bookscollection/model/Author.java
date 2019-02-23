package pl.bookscollection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Author {

  @NonNull
  private String name;
  @NonNull
  private String lastName;
}
