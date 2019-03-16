package pl.bookscollection.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class Message {

  @NonNull
  private String message;
}
