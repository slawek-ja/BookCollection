package pl.bookscollection.service;

public class ServiceOperationException extends Exception {
  public ServiceOperationException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
