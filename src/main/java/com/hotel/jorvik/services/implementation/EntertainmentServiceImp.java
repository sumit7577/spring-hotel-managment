package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Entertainment;
import com.hotel.jorvik.models.EntertainmentType;
import com.hotel.jorvik.repositories.EntertainmentRepository;
import com.hotel.jorvik.repositories.EntertainmentTypeRepository;
import com.hotel.jorvik.services.EntertainmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.hotel.jorvik.util.Tools.parseDate;

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
    public List<Entertainment> getAllEntertainmentElementsByAvailableDate(String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo) {
        Timestamp dateTimeFrom = parseDate(dateFrom, timeFrom);
        Timestamp dateTimeTo = parseDate(dateTo, timeTo);

        List<EntertainmentType> entertainmentTypes = entertainmentTypeRepository.findAll();
        Optional<EntertainmentType> type = entertainmentTypes.stream()
                .filter(entertainmentType1 -> entertainmentType1
                        .getName()
                        .equals(entertainmentType))
                .findFirst();
        if (type.isEmpty()) {
            throw new IllegalArgumentException("Entertainment type not found");
        }

        return entertainmentRepository
                .findAvailableEntertainmentsByTypeAndTime(type.get().getId(), dateTimeFrom, dateTimeTo);
    }

    @Override
    public void updatePrices(List<EntertainmentType> entertainmentTypes) {
        entertainmentTypes.forEach(entertainmentType -> {
            Optional<EntertainmentType> type = entertainmentTypeRepository.findById(entertainmentType.getId());
            if (type.isEmpty()) {
                throw new IllegalArgumentException("Entertainment type not found");
            }
            System.out.println(entertainmentType.getPrice());
            type.get().setPrice(entertainmentType.getPrice());
            entertainmentTypeRepository.save(type.get());
        });
    }
}
