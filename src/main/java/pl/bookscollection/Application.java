package pl.bookscollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.bookscollection.database.HibernateRepository;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;

@SpringBootApplication
public class Application implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  private HibernateRepository hibernateRepository;

  @Override
  public void run(String... args) throws Exception {
    hibernateRepository.save(new Book("Lolek", Cover.HARD, new Author("Andzrej", "Byk")));
    hibernateRepository.findAll().forEach(System.out::println);
    System.out.println(hibernateRepository.existsById(1L));
  }
}
