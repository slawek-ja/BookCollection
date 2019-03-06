package pl.bookscollection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  @NonNull
  @Column(name = "author_name")
  private String name;
  @NonNull
  @Column(name = "author_last_name")
  private String lastName;

  public Author(@NonNull String name, @NonNull String lastName) {
    this.name = name;
    this.lastName = lastName;
  }
}
