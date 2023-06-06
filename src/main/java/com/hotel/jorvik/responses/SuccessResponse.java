package com.hotel.jorvik.responses;

import lombok.Data;

@Data
public class SuccessResponse<T> implements Response{
    private ResponseStatus status = ResponseStatus.success;
    private T data;

    public SuccessResponse(T data) {
        this.data = data;
    }
}
