package pl.bookscollection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "author_name")
  private String name;

  @Column(name = "author_last_name")
  private String lastName;

  public Author(@NonNull String name, @NonNull String lastName) {
    this.name = name;
    this.lastName = lastName;
  }
}
