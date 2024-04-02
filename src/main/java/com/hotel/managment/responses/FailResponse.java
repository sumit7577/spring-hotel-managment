package com.hotel.managment.responses;

import lombok.Data;

/**
 * Represents a failure HTTP response holding data.
 *
 * @param <T> The type of data in the response.
 */
@Data
public class FailResponse<T> implements Response {
  private ResponseStatus status = ResponseStatus.fail;
  private T data;

  /**
   * Constructs a FailResponse with the given data.
   *
   * @param data The data associated with the failure.
   */
  public FailResponse(T data) {
    this.data = data;
  }
}
