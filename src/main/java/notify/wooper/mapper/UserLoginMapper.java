package notify.wooper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLoginMapper {

	@Select("SELECT COUNT(1) FROM USER_LOGIN WHERE USER_ID = #{userId} AND PASSWORD = #{password}")
	Integer countByUserIdAndPassword(String userId, String password);
}
