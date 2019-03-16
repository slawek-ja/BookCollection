package pl.bookscollection.database;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.bookscollection.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
  @Modifying
  @Query(value = "Delete from book where id = ?1", nativeQuery = true)
  void deleteById(Long id);

  @Modifying
  default void deleteAll() {
    deleteBooks();
    deleteAuthor();
  }

  @Modifying
  @Query(value = "Delete from book", nativeQuery = true)
  void deleteBooks();

  @Modifying
  @Query(value = "Delete from author", nativeQuery = true)
  void deleteAuthor();
}
