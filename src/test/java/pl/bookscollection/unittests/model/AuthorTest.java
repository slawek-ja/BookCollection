package pl.bookscollection.unittests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bookscollection.model.Author;

class AuthorTest {

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private Validator validator;

  @BeforeEach
  void setUpValidator() {
    validator = factory.getValidator();
  }

  @Test
  void shouldInvokeClass() {
    //given
    Author author;

    //when
    author = new Author("Tim", "Restwood");

    //then
    assertNotNull(author);
  }

  @Test
  void shouldGetValidValuesFromClass() {
    //given
    String expectedName = "Steve";
    String expectedLastName = "Fox";
    Author author = new Author(expectedName, expectedLastName);

    //when
    String nameResult = author.getName();
    String lastNameResult = author.getLastName();


    //then
    assertEquals(expectedName, nameResult);
    assertEquals(expectedLastName, lastNameResult);
  }

  @Test
  void shouldThrowNullPointerExceptionWhenNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Author(null, "Restwood"));
  }

  @Test
  void shouldThrowNullPointerExceptionWhenLastNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Author("Tim", null));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldCatchInvalidArgumentsWhenNameAndLastNameAreEmptyTest")
  void shouldCatchInvalidArgumentsWhenNameAndLastNameAreEmpty(String name, String lastName) {
    //given
    Author authorToValidate = new Author(name, lastName);
    int expectedViolationsSize = 4;

    //when
    Set<ConstraintViolation<Author>> expectedViolations = validator.validate(authorToValidate);

    //then
    assertEquals(expectedViolationsSize, expectedViolations.size());
  }

  private static Stream<Arguments> getArgumentsForShouldCatchInvalidArgumentsWhenNameAndLastNameAreEmptyTest() {
    return Stream.of(
            Arguments.arguments("", ""),
            Arguments.arguments("   ", "   "),
            Arguments.arguments("  ", ""),
            Arguments.arguments("", "  "));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldCatchInvalidArgumentsWhenNameIsWrong")
  void shouldCatchInvalidArgumentsWhenNameIsWrong(String name) {
    //given
    Author authorToValidate = new Author(name, "Fox");
    int expectedViolationsSize = 1;
    String expectedValidationMessage = "Invalid name, check passed data.";

    //when
    Set<ConstraintViolation<Author>> expectedViolations = validator.validate(authorToValidate);
    String resultValidationMessage = expectedViolations.iterator().next().getMessage();
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
    assertEquals(expectedValidationMessage, resultValidationMessage);
  }

  private static Stream<Arguments> getArgumentsForShouldCatchInvalidArgumentsWhenNameIsWrong() {
    return Stream.of(
            Arguments.arguments("!@#$%^&*()<>?/';"),
            Arguments.arguments("Abb     "),
            Arguments.arguments("    Steve"),
            Arguments.arguments("Steve!@#"),
            Arguments.arguments("Steven12345"));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldCatchInvalidArgumentsWhenLastNameIsWrong")
  void shouldCatchInvalidArgumentsWhenLastNameIsWrong(String lastName) {
    //given
    Author authorToValidate = new Author("Steve", lastName);
    int expectedViolationsSize = 1;
    String expectedValidationMessage = "Invalid last name, check passed data.";

    //when
    Set<ConstraintViolation<Author>> expectedViolations = validator.validate(authorToValidate);
    String resultValidationMessage = expectedViolations.iterator().next().getMessage();
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
    assertEquals(expectedValidationMessage, resultValidationMessage);
  }

  private static Stream<Arguments> getArgumentsForShouldCatchInvalidArgumentsWhenLastNameIsWrong() {
    return Stream.of(
            Arguments.arguments("!@#$%^&*()<>?/';"),
            Arguments.arguments("Abb     "),
            Arguments.arguments("    Fox"),
            Arguments.arguments("Foxy!@#"),
            Arguments.arguments("Foxy12345"));
  }

  @ParameterizedTest
  @MethodSource("getArgumentsForShouldPassValidationProcess")
  void shouldPassValidationProcess(String name, String lastName) {
    //given
    Author authorToValidate = new Author(name, lastName);
    int expectedViolationsSize = 0;

    //when
    Set<ConstraintViolation<Author>> expectedViolations = validator.validate(authorToValidate);
    int resultViolationsSize = expectedViolations.size();

    //then
    assertEquals(expectedViolationsSize, resultViolationsSize);
  }

  private static Stream<Arguments> getArgumentsForShouldPassValidationProcess() {
    return Stream.of(
            Arguments.arguments("Steve", "Fox"),
            Arguments.arguments("Stan", "Winfield I"),
            Arguments.arguments("Dan Steven", "Ground"));
  }
}
