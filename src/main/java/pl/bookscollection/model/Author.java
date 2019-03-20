package pl.bookscollection.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of author", example = "17")
  long id;

  @Column(name = "author_name")
  @ApiModelProperty(value = "The name of author", example = "Steve", required = true)
  private String name;

  @Column(name = "author_last_name")
  @ApiModelProperty(value = "The last name of author", example = "Fox", required = true)
  private String lastName;

  public Author(@NonNull String name, @NonNull String lastName) {
    this.name = name;
    this.lastName = lastName;
  }
}
