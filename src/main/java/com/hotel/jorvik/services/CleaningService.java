package com.hotel.jorvik.services;

import com.hotel.jorvik.models.dto.cleaning.CleaningResponse;
import java.util.List;

/**
 * Interface for managing cleaning services in the application.
 *
 * <p>This interface provides methods for retrieving a list of rooms that require cleaning and for
 * marking a specific room as cleaned.
 */
public interface CleaningService {

  List<CleaningResponse> getRoomsToClean();

  void cleanRoom(int roomId);
}
