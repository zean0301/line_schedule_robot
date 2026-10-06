package notify.wooper.service;

import notify.wooper.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    public void insertSchedule(String content, String datetime) {
        //todo 日期時間格式檢查、轉換
        scheduleMapper.insertSchedule(content,  datetime);
    }
}
