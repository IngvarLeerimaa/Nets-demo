package com.example.Nets_demo.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class NumNotFoundException extends Exception {
  public NumNotFoundException(String message) {
    super(message);
  }
}
