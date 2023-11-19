package com.hotel.jorvik.responses;

import lombok.Data;

/**
 * Represents a success HTTP response holding data.
 *
 * @param <T> The type of data in the response.
 */
@Data
public class SuccessResponse<T> implements Response {
  private ResponseStatus status = ResponseStatus.success;
  private T data;

  /**
   * Constructs a SuccessResponse with the given data.
   *
   * @param data The data to be included in the response.
   */
  public SuccessResponse(T data) {
    this.data = data;
  }
}
