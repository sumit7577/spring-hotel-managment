package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Place;
import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.repositories.PlaceRepository;
import com.hotel.jorvik.repositories.WeekendRepository;
import com.hotel.jorvik.services.WeekendService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeekendServiceImp implements WeekendService {

    private final WeekendRepository weekendRepository;
    private final PlaceRepository placeRepository;

    @Override
    public Weekend getById(int id){
        Optional<Weekend> weekend = weekendRepository.findById(id);
        if (weekend.isEmpty()){
            throw new NoSuchElementException("Weekend not found");
        }
        return weekend.get();
    }

    @Override
    public Iterable<Weekend> getByDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not correct");
        }
        return weekendRepository.findByDate(date);
    }

    @Override
    @Transactional
    public void updateById(int id, Weekend newWeekend) {
        Optional<Weekend> weekendToEdit = weekendRepository.findById(id);
        if (weekendToEdit.isEmpty()) {
            throw new NoSuchElementException("Weekend not found");
        }
        Optional<Place> newPlace = placeRepository.findByName(newWeekend.getPlace().getName());
        if (newPlace.isEmpty()){
            throw new NoSuchElementException("Place not found");
        }
        Weekend weekend = weekendToEdit.get();
        weekend.setPlace(newPlace.get());
        weekend.setDescription(newWeekend.getDescription());
        weekend.setDateFrom(newWeekend.getDateFrom());
        weekend.setDateTo(newWeekend.getDateTo());
        weekendRepository.save(weekend);
    }

    public void deleteById(int id) {
        Optional<Weekend> weekendToEdit = weekendRepository.findById(id);
        if (weekendToEdit.isEmpty()) {
            throw new NoSuchElementException("Weekend not found");
        }
        weekendRepository.deleteById(id);
    }

    @Override
    public Weekend create(Weekend weekend) {
        weekendRepository.save(weekend);
        return weekend;
    }
}
