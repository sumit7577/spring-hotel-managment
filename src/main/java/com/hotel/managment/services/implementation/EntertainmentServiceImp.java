package com.hotel.managment.services.implementation;

import static com.hotel.managment.util.Tools.parseDate;

import com.hotel.managment.models.Entertainment;
import com.hotel.managment.models.EntertainmentType;
import com.hotel.managment.repositories.EntertainmentRepository;
import com.hotel.managment.repositories.EntertainmentTypeRepository;
import com.hotel.managment.services.EntertainmentService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation for managing entertainment-related services in the application.
 *
 * <p>This service provides methods for retrieving entertainment types and elements, as well as
 * updating prices for different types of entertainment services. It allows querying entertainment
 * options based on availability and managing their pricing.
 */
@Service
@RequiredArgsConstructor
public class EntertainmentServiceImp implements EntertainmentService {

  private final EntertainmentTypeRepository entertainmentTypeRepository;
  private final EntertainmentRepository entertainmentRepository;

  @Override
  public List<EntertainmentType> getAllEntertainmentTypes() {
    return entertainmentTypeRepository.findAll();
  }

  @Override
  public List<Entertainment> getAllEntertainmentElementsByAvailableDate(
      String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo) {
    Timestamp dateTimeFrom = parseDate(dateFrom, timeFrom);
    Timestamp dateTimeTo = parseDate(dateTo, timeTo);

    List<EntertainmentType> entertainmentTypes = entertainmentTypeRepository.findAll();
    Optional<EntertainmentType> type =
        entertainmentTypes.stream()
            .filter(entertainmentType1 -> entertainmentType1.getName().equals(entertainmentType))
            .findFirst();
    if (type.isEmpty()) {
      throw new IllegalArgumentException("Entertainment type not found");
    }

    return entertainmentRepository.findAvailableEntertainmentsByTypeAndTime(
        type.get().getId(), dateTimeFrom, dateTimeTo);
  }

  @Override
  public void updatePrices(List<EntertainmentType> entertainmentTypes) {
    entertainmentTypes.forEach(
        entertainmentType -> {
          Optional<EntertainmentType> type =
              entertainmentTypeRepository.findById(entertainmentType.getId());
          if (type.isEmpty()) {
            throw new IllegalArgumentException("Entertainment type not found");
          }
          System.out.println(entertainmentType.getPrice());
          type.get().setPrice(entertainmentType.getPrice());
          entertainmentTypeRepository.save(type.get());
        });
  }
}
