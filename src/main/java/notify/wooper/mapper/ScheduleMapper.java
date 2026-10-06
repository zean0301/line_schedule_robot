package notify.wooper.mapper;

import notify.wooper.entity.Schedule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Insert("INSERT INTO SCHEDULE (content, datetime, status) VALUES (#{content}, #{datetime}, #{status})")
    void insertSchedule(@Param("content") String content, @Param("datetime") String datetime, @Param("status") int status);

    @Select("SELECT * FROM SCHEDULE WHERE status = #{status}")
    List<Schedule> getSchedulesByStatus(@Param("status") int status);

    @Delete("DELETE FROM SCHEDULE WHERE id = #{scheduleId}")
    void deleteSchedule(@Param("scheduleId") int scheduleId);
}
