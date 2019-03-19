package pl.bookscollection.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.bookscollection.controller.Message;
import pl.bookscollection.generators.BookGenerator;
import pl.bookscollection.model.Book;
import pl.bookscollection.service.BookService;
import pl.bookscollection.service.ServiceOperationException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

  private final String urlTemplate = "/books/%s";
  private ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService service;

  @BeforeEach
  void setUpMapper() {
    mapper = new ObjectMapper();
  }

  @Test
  void shouldGetAllBooks() throws Exception {
    //given
    List<Book> expectedBooks = Arrays.asList(
            BookGenerator.getBookWithSpecifiedTitle("Sample title 1"),
            BookGenerator.getBookWithSpecifiedTitle("Sample title 2"),
            BookGenerator.getBookWithSpecifiedTitle("Sample title 3"));
    when(service.getAllBooks()).thenReturn(expectedBooks);

    //when
    MvcResult result = mockMvc.perform(get(String.format(urlTemplate, "")).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
    List<Book> resultBooks = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {
    });
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedBooks, resultBooks);
    assertEquals(HttpStatus.OK.value(), resultStatus);
    verify(service).getAllBooks();
  }

  @Test
  void shouldReturnInternalServerErrorDuringGettingAllBooksWhenSomethingWentWrongOnServer() throws Exception {
    //given
    Message expectedMessage = new Message("Internal server error while getting books.");
    when(service.getAllBooks()).thenThrow(ServiceOperationException.class);

    //when
    MvcResult result = mockMvc.perform(get(String.format(urlTemplate, "")).contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
    assertEquals(expectedMessage, resultMessage);
    verify(service).getAllBooks();
  }

  @Test
  void shouldGetSpecifiedBook() throws Exception {
    //given
    Book expected = BookGenerator.getBookWithSpecifiedTitle("Sample title");
    when(service.getBook(expected.getId())).thenReturn(Optional.of(expected));

    //when
    MvcResult result = mockMvc.perform(get(String.format(urlTemplate, expected.getId()))).andReturn();
    Book resultBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expected, resultBook);
    assertEquals(HttpStatus.OK.value(), resultStatus);
    verify(service).getBook(expected.getId());
  }

  @Test
  void shouldReturnNotFoundStatusWhenBookNotExisting() throws Exception {
    //given
    long id = 1;
    Message expectedMessage = new Message("Book not found with passed id");
    when(service.getBook(id)).thenReturn(Optional.empty());

    //when
    MvcResult result = mockMvc.perform(get(String.format(urlTemplate, id))).andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.NOT_FOUND.value(), resultStatus);
    verify(service).getBook(id);
  }

  @Test
  void shouldReturnInternalServerErrorDuringGettingSpecifiedBookWhenSomethingWentWrongOnServer() throws Exception {
    //given
    long id = 1;
    Message expectedMessage = new Message(String.format("Internal server error while getting book by id: %d.", id));
    when(service.getBook(id)).thenThrow(ServiceOperationException.class);

    //when
    MvcResult result = mockMvc.perform(get(String.format(urlTemplate, id))).andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
    verify(service).getBook(id);
  }

  @Test
  void shouldAddBook() throws Exception {
    //given
    Book bookToAdd = BookGenerator.getBook();
    Book expectedBook = BookGenerator.getBookWithSpecifiedId(1);
    String bookAsJson = mapper.writeValueAsString(bookToAdd);
    when(service.addBook(bookToAdd)).thenReturn(expectedBook);

    //when
    MvcResult result = mockMvc.perform(post(String.format(urlTemplate, ""))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(bookAsJson))
            .andReturn();
    Book resultBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedBook, resultBook);
    assertEquals(HttpStatus.CREATED.value(), resultStatus);
    verify(service).addBook(bookToAdd);
  }

  @Test
  void shouldReturnInternalServiceErrorDuringAddingBookWhenSomethingWentWrongOnServer() throws Exception {
    //given
    Book bookToAdd = BookGenerator.getBook();
    String bookToAddAsJson = mapper.writeValueAsString(bookToAdd);
    Message expectedMessage = new Message("Internal server error while adding new book");
    when(service.addBook(bookToAdd)).thenThrow(ServiceOperationException.class);

    //when
    MvcResult result = mockMvc.perform(post(String.format(urlTemplate, ""))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(bookToAddAsJson))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
  }

  @Test
  void shouldUpdateBook() throws Exception {
    //given
    Book expectedBook = BookGenerator.getBook();
    String expectedBookAsJson = mapper.writeValueAsString(expectedBook);
    when(service.getBook(expectedBook.getId())).thenReturn(Optional.of(expectedBook));
    when(service.addBook(expectedBook)).thenReturn(expectedBook);

    //when
    MvcResult result = mockMvc.perform(put(String.format(urlTemplate, expectedBook.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(expectedBookAsJson))
            .andReturn();
    Book resultBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedBook, resultBook);
    assertEquals(HttpStatus.OK.value(), resultStatus);
    verify(service).getBook(expectedBook.getId());
  }

  @Test
  void shouldReturnBadRequestDuringUpdatingBookWithWrongId() throws Exception {
    //given
    Book book = BookGenerator.getBookWithSpecifiedId(1);
    Message expectedMessage = new Message("Passed data is invalid. Please verify book id");
    String bookAsJson = mapper.writeValueAsString(book);
    when(service.getBook(book.getId())).thenReturn(Optional.empty());

    //when
    MvcResult result = mockMvc.perform(put(String.format(urlTemplate, book.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(bookAsJson))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.BAD_REQUEST.value(), resultStatus);
    verify(service).getBook(book.getId());
  }

  @Test
  void shouldThrowInternalServerErrorDuringUpdatingWhenSomethingWentWrongWithServer() throws Exception {
    //given
    Book book = BookGenerator.getBook();
    Message expectedMessage = new Message("Internal server error while updating specified book");
    String bookAsJson = mapper.writeValueAsString(book);
    when(service.getBook(book.getId())).thenThrow(ServiceOperationException.class);

    //when
    MvcResult result = mockMvc.perform(put(String.format(urlTemplate, book.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(bookAsJson))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
    verify(service).getBook(book.getId());
  }

  @Test
  void shouldDeleteSpecifiedBook() throws Exception {
    //given
    Book bookToDelete = BookGenerator.getBookWithSpecifiedId(1);
    String bookToDeleteAsJson = mapper.writeValueAsString(bookToDelete);
    when(service.getBook(bookToDelete.getId())).thenReturn(Optional.of(bookToDelete));
    doNothing().when(service).deleteBook(bookToDelete.getId());

    //when
    MvcResult result = mockMvc.perform(delete(String.format(urlTemplate, bookToDelete.getId()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(bookToDeleteAsJson))
            .andReturn();
    Book deletedBook = mapper.readValue(result.getResponse().getContentAsString(), Book.class);
    int statusResult = result.getResponse().getStatus();

    //then
    assertEquals(bookToDelete, deletedBook);
    assertEquals(HttpStatus.OK.value(), statusResult);
    verify(service).getBook(bookToDelete.getId());
    verify(service).deleteBook(bookToDelete.getId());
  }

  @Test
  void shouldReturnNotFoundDuringDeletingSpecifiedBookWithWrongId() throws Exception {
    //given
    long id = 1;
    Message expectedMessage = new Message("Book not found");
    when(service.getBook(id)).thenReturn(Optional.ofNullable(null));

    //when
    MvcResult result = mockMvc.perform(delete(String.format(urlTemplate, id)))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.NOT_FOUND.value(), resultStatus);
    verify(service).getBook(id);
  }

  @Test
  void shouldThrowInternalServerErrorDuringDeletingSpecifiedBookWhenSomethingWentWrongWithServer() throws Exception {
    //given
    long id = 1;
    Message expectedMessage = new Message("Internal server error while deleting specified book");
    when(service.getBook(id)).thenThrow(ServiceOperationException.class);

    //when
    MvcResult result = mockMvc.perform(delete(String.format(urlTemplate, id)))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
    verify(service).getBook(id);
  }

  @Test
  void shouldDeleteAllBooks() throws Exception {
    //given
    Message expectedMessage = new Message("Deleted all books");
    doNothing().when(service).deleteAllBooks();

    //when
    MvcResult result = mockMvc.perform(delete(String.format(urlTemplate, "deleteAll")))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.OK.value(), resultStatus);
    verify(service).deleteAllBooks();
  }

  @Test
  void shouldThrowInternalServerErrorDuringDeletingAllBooksWhenSomethingWentWrongWithServer() throws Exception {
    //given
    Message expectedMessage = new Message("Internal server error while deleting all books");
    doThrow(ServiceOperationException.class).when(service).deleteAllBooks();

    //when
    MvcResult result = mockMvc.perform(delete(String.format(urlTemplate, "deleteAll")))
            .andReturn();
    Message resultMessage = mapper.readValue(result.getResponse().getContentAsString(), Message.class);
    int resultStatus = result.getResponse().getStatus();

    //then
    assertEquals(expectedMessage, resultMessage);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultStatus);
    verify(service).deleteAllBooks();
  }
}