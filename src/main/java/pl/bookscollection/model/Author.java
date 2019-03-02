package pl.bookscollection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Author {

  @NonNull
  private String name;
  @NonNull
  private String lastName;
}
