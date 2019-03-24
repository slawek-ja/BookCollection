package pl.bookscollection.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "The id of book.", example = "10")
  private long id;

  @Column(name = "book_title")
  @NotBlank(message = "Title of the book cannot be empty.")
  @ApiModelProperty(value = "The book title.", example = "Design patterns", required = true)
  private String title;

  @Column(name = "book_cover_type")
  @Enumerated(value = EnumType.STRING)
  @ApiModelProperty(value = "The type of book cover.", example = "SOFT", required = true)
  private Cover cover;

  @Valid
  @ManyToOne(cascade = CascadeType.ALL)
  private Author author;

  public Book(@NonNull String title, @NonNull Cover cover, @NonNull Author author) {
    this.id = -1;
    this.title = title;
    this.cover = cover;
    this.author = author;
  }
}
