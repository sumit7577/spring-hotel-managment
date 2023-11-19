package com.hotel.jorvik.responses;

import lombok.Data;

/** Represents an error HTTP response with a message. */
@Data
public class ErrorResponse implements Response {
  private ResponseStatus status = ResponseStatus.error;
  private String message;

  /**
   * Constructs an ErrorResponse with a specific message.
   *
   * @param message The error message.
   */
  public ErrorResponse(String message) {
    this.message = message;
  }
}
