package pl.bookscollection.unittests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Author;
import pl.bookscollection.model.Book;
import pl.bookscollection.model.Cover;
import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class BookTest {

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private Validator validator;

  @BeforeEach
  void setUpValidator() {
    validator = factory.getValidator();
  }

  @Test
  void shouldInvokeClass() {
    //given
    Author author = new Author("Steve", "Fox");
    Cover cover = Cover.HARD;
    Book book;

    //when
    book = new Book("Green Woods", cover, author);

    //then
    assertNotNull(book);
  }

  @Test
  void shouldGetValidValuesFromClass() {
    //given
    String expectedTitle = "Green Woods";
    Cover expectedCover = Cover.SOFT;
    Author expectedAuthor = new Author("Steve", "Fox");
    Book book = new Book(expectedTitle, expectedCover, expectedAuthor);

    //when
    String titleResult = book.getTitle();
    Cover coverResult = book.getCover();
    final Author authorResult = book.getAuthor();


    //then
    assertEquals(expectedTitle, titleResult);
    assertEquals(expectedCover, coverResult);
    assertEquals(expectedAuthor, authorResult);
  }

  @Test
  void shouldThrowNullPointerExceptionWhenTitleIsNull() {
    assertThrows(NullPointerException.class, () -> new Book(null, Cover.HARD, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenCoverIsNull() {
    assertThrows(NullPointerException.class, () -> new Book("Green Woods", null, new Author("Steve", "Last")));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenAuthorIsNull() {
    assertThrows(NullPointerException.class, () -> new Book("Green Woods", Cover.SOFT, null));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldCatchInvalidArgumentsWhenTitleIsEmpty")
  void shouldCatchInvalidArgumentsWhenTitleIsEmpty(String title) {
    //given
    Book bookToValidate = BookGenerator.getBookWithSpecifiedTitle(title);
    int expectedViolationsSize = 1;
    String expectedValidationMessage = "Title of the book cannot be empty";

    //when
    Set<ConstraintViolation<Book>> expectedViolations = validator.validate(bookToValidate);
    String resultValidationMessage = expectedViolations.iterator().next().getMessage();
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
    assertEquals(expectedValidationMessage, resultValidationMessage);
  }

  private static Stream<Arguments> getArgumentsForShouldCatchInvalidArgumentsWhenTitleIsEmpty() {
    return Stream.of(
            Arguments.arguments(""),
            Arguments.arguments("   "));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldPassValidationProcess")
  void shouldPassValidationProcess(String title) {
    //given
    Book bookToValidate = BookGenerator.getBookWithSpecifiedTitle(title);
    int expectedViolationsSize = 0;

    //when
    Set<ConstraintViolation<Book>> expectedViolations = validator.validate(bookToValidate);
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
  }

  private static Stream<Arguments> getArgumentsForShouldPassValidationProcess() {
    return Stream.of(
            Arguments.arguments("Some title"),
            Arguments.arguments("SampleTitle"));
  }

  @Test
  void shouldCatchInvalidArgumentsWhenAuthorNotPassedValidation() {
    //given
    Author author = new Author("", "");
    Book bookToValidate = BookGenerator.getBookWithSpecifiedAuthor(author);
    int expectedViolationsSize = 4;

    //when
    Set<ConstraintViolation<Book>> expectedViolations = validator.validate(bookToValidate);
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
  }
}
