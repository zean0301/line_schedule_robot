package notify.wooper.dto;

public record QueryScheduleResponse (
    int id,
    String content,
    String datetime,
    int status
) {}
