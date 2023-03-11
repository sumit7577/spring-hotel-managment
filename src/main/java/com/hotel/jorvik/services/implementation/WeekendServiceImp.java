package com.hotel.jorvik.services.implementation;

import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.repositories.WeekendRepository;
import com.hotel.jorvik.services.interfaces.WeekendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class WeekendServiceImp implements WeekendService {

    private final WeekendRepository repository;

    @Autowired
    public WeekendServiceImp(WeekendRepository repository) {
        this.repository = repository;
    }

    @Override
    public Weekend getWeekendById(int id){
        Optional<Weekend> weekend = repository.findById(id);
        return weekend.orElse(null);
    }

    @Override
    public Iterable<Weekend> getWeekendsByDate(Date date) {
        return repository.findByDate(date);
    }

    @Override
    public boolean updateWeekendById(int id, Weekend newWeekend) {
        Optional<Weekend> weekendToEdit = repository.findById(id);
        if (weekendToEdit.isEmpty())
            return false;
        Weekend weekend = weekendToEdit.get();
        weekend.setPlace(newWeekend.getPlace());
        weekend.setDescription(newWeekend.getDescription());
        weekend.setDateFrom(newWeekend.getDateFrom());
        weekend.setDateTo(newWeekend.getDateTo());
        repository.save(weekend);
        return true;
    }

    public void deleteWeekendById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void createWeekend(Weekend weekend) {
        repository.save(weekend);
    }
}
