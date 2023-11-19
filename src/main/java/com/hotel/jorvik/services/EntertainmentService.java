package com.hotel.jorvik.services;

import com.hotel.jorvik.models.Entertainment;
import com.hotel.jorvik.models.EntertainmentType;
import java.util.List;

/**
 * Interface for managing entertainment-related services in the application.
 *
 * <p>This interface provides methods for retrieving entertainment types and elements, as well as
 * updating prices for different types of entertainment services. It allows querying entertainment
 * options based on availability and managing their pricing.
 */
public interface EntertainmentService {
  List<EntertainmentType> getAllEntertainmentTypes();

  List<Entertainment> getAllEntertainmentElementsByAvailableDate(
      String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo);

  void updatePrices(List<EntertainmentType> entertainmentTypes);
}
