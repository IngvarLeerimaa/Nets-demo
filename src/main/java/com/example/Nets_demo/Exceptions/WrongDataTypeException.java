package com.example.Nets_demo.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
public class WrongDataTypeException extends Exception {
  public WrongDataTypeException(String message) {
    super(message);
  }
}
