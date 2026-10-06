package notify.wooper.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private int id;
    private String content;
    private LocalDateTime datetime;
    /*
        status
        0: unsend
        1: success
        2: fail
     */
    private int status;
}
