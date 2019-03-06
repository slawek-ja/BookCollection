package pl.bookscollection.database;

import org.springframework.data.repository.CrudRepository;
import pl.bookscollection.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
