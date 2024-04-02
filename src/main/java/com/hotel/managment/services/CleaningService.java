package com.hotel.managment.services;

import com.hotel.managment.models.dto.cleaning.CleaningResponse;
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
