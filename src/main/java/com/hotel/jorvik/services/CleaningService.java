package com.hotel.jorvik.services;

import com.hotel.jorvik.models.DTO.cleaning.CleaningResponse;

import java.util.List;

public interface CleaningService {

    public List<CleaningResponse> getRoomsToClean();
    public void cleanRoom(int roomId);
}
