package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.repositories.WeekendRepository;
import com.hotel.jorvik.services.interfaces.WeekendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeekendServiceImp implements WeekendService {

    private final WeekendRepository repository;

    @Override
    public Weekend getById(int id){
        Optional<Weekend> weekend = repository.findById(id);
        return weekend.orElse(null);
    }

    @Override
    public Iterable<Weekend> getByDate(String date) {
        return repository.findByDate(date);
    }

    @Override
    public boolean updateById(int id, Weekend newWeekend) {
        Optional<Weekend> weekendToEdit = repository.findById(id);
        if (weekendToEdit.isEmpty()) {
            return false;
        }
        Weekend weekend = weekendToEdit.get();
        weekend.setPlace(newWeekend.getPlace());
        weekend.setDescription(newWeekend.getDescription());
        weekend.setDateFrom(newWeekend.getDateFrom());
        weekend.setDateTo(newWeekend.getDateTo());
        repository.save(weekend);
        return true;
    }

    public boolean deleteById(int id) {
        Optional<Weekend> weekendToEdit = repository.findById(id);
        if (weekendToEdit.isEmpty())
            return false;
        repository.deleteById(id);
        return true;
    }

    @Override
    public void create(Weekend weekend) {
        repository.save(weekend);
    }
}
