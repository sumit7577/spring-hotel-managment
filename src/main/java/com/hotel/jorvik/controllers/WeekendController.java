package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.Weekend;
import com.hotel.jorvik.response.ErrorResponse;
import com.hotel.jorvik.services.interfaces.WeekendService;
import com.hotel.jorvik.response.FailResponse;
import com.hotel.jorvik.response.Response;
import com.hotel.jorvik.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/weekend")
public class WeekendController {

    private final WeekendService service;

    @Autowired
    public WeekendController(WeekendService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable Integer id) {
        Weekend weekend = service.getWeekendById(id);
        if (weekend == null) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("Weekend not found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(weekend));
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<Response> getByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parse(date);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(BAD_REQUEST).body(new FailResponse<>("Date format is not correct"));
        }
        Iterable<Weekend> weekends = service.getWeekendsByDate(date);
        if (!weekends.iterator().hasNext()) {
            return ResponseEntity.status(NOT_FOUND).body(new FailResponse<>("Weekends not found"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(weekends));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Integer id, @RequestBody Weekend weekend){
        if (service.updateWeekendById(id, weekend)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse("Weekend not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Integer id) {
        if (service.deleteWeekendById(id)) {
            return ResponseEntity.ok().body(new SuccessResponse<>(null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse("Weekend not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<Response> create(@RequestBody Weekend weekend) {
        service.createWeekend(weekend);
        return ResponseEntity.ok().body(new SuccessResponse<>(null));
    }
}
