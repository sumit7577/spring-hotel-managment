package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.services.interfaces.WeekendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/api/weekend")
public class WeekendController {

    private final WeekendService service;

    @Autowired
    public WeekendController(WeekendService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Weekend> getWeekendById(@PathVariable Integer id){
        Weekend weekend = service.getWeekendById(id);
        if (weekend == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(weekend);
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<Iterable<Weekend>> getWeekendsByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)  {
        Iterable<Weekend> weekends = service.getWeekendsByDate(date);
        if (!weekends.iterator().hasNext())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(weekends);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Void> updateWeekendById(@PathVariable Integer id, @RequestBody Weekend weekend){
        if (service.updateWeekendById(id, weekend))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWeekend(@PathVariable Integer id) {
        service.deleteWeekendById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createWeekend(@RequestBody Weekend weekend) {
        service.createWeekend(weekend);
        return ResponseEntity.ok().build();
    }
}
