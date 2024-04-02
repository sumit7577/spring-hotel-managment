package com.hotel.managment.services;

import com.hotel.managment.models.Entertainment;
import com.hotel.managment.models.EntertainmentType;
import com.hotel.managment.repositories.EntertainmentRepository;
import com.hotel.managment.repositories.EntertainmentTypeRepository;
import com.hotel.managment.services.implementation.EntertainmentServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntertainmentServiceTest {

  @InjectMocks private EntertainmentServiceImp entertainmentService;
  @Mock private EntertainmentTypeRepository entertainmentTypeRepository;
  @Mock private EntertainmentRepository entertainmentRepository;

  @Test
  void getAllEntertainmentTypes() {
    EntertainmentType type1 = new EntertainmentType("Music", 50);
    EntertainmentType type2 = new EntertainmentType("Movie", 40);

    List<EntertainmentType> types = Arrays.asList(type1, type2);

    when(entertainmentTypeRepository.findAll()).thenReturn(types);

    List<EntertainmentType> result = entertainmentService.getAllEntertainmentTypes();
    assertEquals(types, result);
  }

  @Test
  void getAllEntertainmentElementsByAvailableDate() {
    String dateFrom = "2023-09-07";
    String timeFrom = "14-00";
    String dateTo = "2023-09-07";
    String timeTo = "18-00";
    Timestamp dateTimeFrom = Timestamp.valueOf("2023-09-07 14:00:00");
    Timestamp dateTimeTo = Timestamp.valueOf("2023-09-07 18:00:00");

    EntertainmentType entertainmentType = new EntertainmentType("Tennis", 50);
    entertainmentType.setId(1);

    Entertainment entertainment1 = new Entertainment("Tennis1", 123, entertainmentType);
    Entertainment entertainment2 = new Entertainment("Tennis2", 321, entertainmentType);

    List<Entertainment> expectedEntertainments = Arrays.asList(entertainment1, entertainment2);
    List<EntertainmentType> allTypes = Collections.singletonList(entertainmentType);

    when(entertainmentTypeRepository.findAll()).thenReturn(allTypes);
    when(entertainmentRepository.findAvailableEntertainmentsByTypeAndTime(
            1, dateTimeFrom, dateTimeTo))
        .thenReturn(expectedEntertainments);

    List<Entertainment> actualEntertainments =
        entertainmentService.getAllEntertainmentElementsByAvailableDate(
            "Tennis", dateFrom, timeFrom, dateTo, timeTo);

    assertEquals(expectedEntertainments, actualEntertainments);
  }
}
