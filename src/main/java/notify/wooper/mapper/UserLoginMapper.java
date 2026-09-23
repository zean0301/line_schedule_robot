package notify.wooper.mapper;

import notify.wooper.entity.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLoginMapper {

	@Select("SELECT COUNT(1) FROM USER_LOGIN WHERE USER_ID = #{userId} AND PASSWORD = #{password}")
	Integer countByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

	@Select("SELECT * FROM USER_LOGIN WHERE USER_ID = #{userId}")
	UserLogin getUserByUserId(@Param("userId") String userId);
}
