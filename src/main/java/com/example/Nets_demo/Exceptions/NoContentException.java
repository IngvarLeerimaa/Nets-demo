package com.example.Nets_demo.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
public class NoContentException extends Exception {
  public NoContentException(String message) {
    super(message);
  }
}
