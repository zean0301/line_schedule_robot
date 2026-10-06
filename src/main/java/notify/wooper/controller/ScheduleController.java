package notify.wooper.controller;

import lombok.extern.slf4j.Slf4j;
import notify.wooper.dto.InsertScheduleRequest;
import notify.wooper.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/insert_schedule")
    public ResponseEntity<String> insertSchedule(@RequestBody InsertScheduleRequest request) {
        if (request.content() == null || request.content().isBlank() || request.datetime() == null || request.datetime().isBlank()) {
            return ResponseEntity.badRequest().body("content and datetime are required");
        }

        log.info("/insert_schedule start");
        log.debug("/insert_schedule content: {} / datetime: {}", request.content(),  request.datetime());

        scheduleService.insertSchedule(request.content(), request.datetime());

        log.info("/insert_schedule end");
        return ResponseEntity.ok("Schedule inserted successfully");
    }
}
