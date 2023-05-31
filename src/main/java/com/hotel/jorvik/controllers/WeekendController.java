package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.services.WeekendService;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/weekend")
@RequiredArgsConstructor

public class WeekendController {

    private final WeekendService weekendService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        Weekend weekend = weekendService.getById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(weekend));
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<Response> getByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
        Iterable<Weekend> weekends = weekendService.getByDate(date);
        if (!weekends.iterator().hasNext()) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("Weekends not found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(weekends));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Integer id, @RequestBody Weekend weekend){
        weekendService.updateById(id, weekend);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Integer id) {
        weekendService.deleteById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }

    @PostMapping("/add")
    public ResponseEntity<Response> create(@RequestBody Weekend weekend) {
        Weekend createdWeekend = weekendService.create(weekend);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(createdWeekend));
    }
}
