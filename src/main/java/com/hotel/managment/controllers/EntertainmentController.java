package com.hotel.managment.controllers;

import com.hotel.managment.models.EntertainmentType;
import com.hotel.managment.responses.Response;
import com.hotel.managment.responses.SuccessResponse;
import com.hotel.managment.services.EntertainmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the controller for managing entertainment-related operations in the API. It
 * provides endpoints for retrieving entertainment types, available entertainment elements within a
 * specified time frame, and updating entertainment prices. Access to certain operations is
 * restricted to administrators based on role-based authorization.
 */
@RestController
@RequestMapping("/api/v1/entertainment")
@RequiredArgsConstructor
public class EntertainmentController {

  private final EntertainmentService entertainmentService;

  @GetMapping("/getTypes")
  public ResponseEntity<Response> getTypes() {
    return ResponseEntity.ok()
        .body(new SuccessResponse<>(entertainmentService.getAllEntertainmentTypes()));
  }

  /**
   * Retrieves a list of available entertainment elements of a specific type within a specified time
   * frame.
   *
   * @param entertainmentType The type of entertainment elements to retrieve.
   * @param dateFrom The start date of the time frame for availability checking (yyyy-MM-dd).
   * @param timeFrom The start time of the time frame for availability checking (HH:mm:ss).
   * @param dateTo The end date of the time frame for availability checking (yyyy-MM-dd).
   * @param timeTo The end time of the time frame for availability checking (HH:mm:ss).
   * @return A ResponseEntity with a SuccessResponse containing a list of available entertainment
   *     elements that match the criteria.
   */
  @GetMapping("/getElements/{entertainmentType}/{dateFrom}/{timeFrom}/{dateTo}/{timeTo}")
  public ResponseEntity<Response> getAvailableElements(
      @PathVariable String entertainmentType,
      @PathVariable String dateFrom,
      @PathVariable String timeFrom,
      @PathVariable String dateTo,
      @PathVariable String timeTo) {
    return ResponseEntity.ok()
        .body(
            new SuccessResponse<>(
                entertainmentService.getAllEntertainmentElementsByAvailableDate(
                    entertainmentType, dateFrom, timeFrom, dateTo, timeTo)));
  }

  @PatchMapping("/update-price")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public ResponseEntity<Response> updatePrice(
      @RequestBody List<EntertainmentType> entertainmentTypes) {
    entertainmentService.updatePrices(entertainmentTypes);
    return ResponseEntity.ok().body(new SuccessResponse<>(null));
  }
}
