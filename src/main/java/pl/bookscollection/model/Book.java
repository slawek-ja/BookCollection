package pl.bookscollection.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NonNull
  @Column(name = "book_title")
  private String title;
  @NonNull
  @Column(name = "book_cover_type")
  @Enumerated(value = EnumType.STRING)
  private Cover cover;
  @NonNull
  @ManyToOne(cascade = CascadeType.ALL)
  private Author author;

  public Book(@NonNull String title, @NonNull Cover cover, @NonNull Author author) {
    this.title = title;
    this.cover = cover;
    this.author = author;
  }
}
