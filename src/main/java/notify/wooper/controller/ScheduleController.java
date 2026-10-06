package notify.wooper.controller;

import lombok.extern.slf4j.Slf4j;
import notify.wooper.dto.DeleteScheduleRequest;
import notify.wooper.dto.InsertScheduleRequest;
import notify.wooper.dto.QueryScheduleResponse;
import notify.wooper.entity.Schedule;
import notify.wooper.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/get_unsend_schedule")
    public ResponseEntity<List<QueryScheduleResponse>> getUnsendSchedule() {
        log.info("/get_unsend_schedule start");

        List<QueryScheduleResponse> response = scheduleService.getUnsendSchedule().stream()
                .map(schedule -> new QueryScheduleResponse(
                        schedule.getId(),
                        schedule.getContent(),
                        schedule.getDatetime().toString(),
                        schedule.getStatus()
                ))
                .toList();
        log.info("/get_unsend_schedule end");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete_schedule")
    public ResponseEntity<String> deleteSchedule(@RequestBody DeleteScheduleRequest deleteScheduleRequest) {
        log.info("/delete_schedule start");
        log.debug("/delete_schedule scheduleId: {}", deleteScheduleRequest.id());

        scheduleService.deleteSchedule(deleteScheduleRequest.id());

        log.info("/delete_schedule end");
        return ResponseEntity.ok("Schedule deleted successfully");
    }
}
