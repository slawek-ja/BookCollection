package pl.bookscollection.database;

import org.springframework.data.repository.CrudRepository;
import pl.bookscollection.model.Book;

public interface HibernateRepository extends CrudRepository<Book, Long> {
}
