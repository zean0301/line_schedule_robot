package notify.wooper.service;

import notify.wooper.entity.Schedule;
import notify.wooper.exception.InvalidDateTimeFormatException;
import notify.wooper.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ScheduleService {

    private static final DateTimeFormatter SCHEDULE_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Autowired
    private ScheduleMapper scheduleMapper;

    public void insertSchedule(String content, String datetime) {
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(datetime, SCHEDULE_DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTimeFormatException();
        }

        String normalizedDateTime = parsedDateTime.format(SCHEDULE_DATETIME_FORMATTER);
        scheduleMapper.insertSchedule(content, normalizedDateTime, 0);
    }

    public List<Schedule> getUnsendSchedule() {
        return scheduleMapper.getSchedulesByStatus(0);
    }

    public void deleteSchedule(int scheduleId) {
        scheduleMapper.deleteSchedule(scheduleId);
    }
}
