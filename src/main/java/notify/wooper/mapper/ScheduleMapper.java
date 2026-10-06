package notify.wooper.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ScheduleMapper {

    @Insert("INSERT INTO SCHEDULE (content, datetime) VALUES (#{content}, #{datetime})")
    void insertSchedule(@Param("content") String content, @Param("datetime") String datetime);
}
