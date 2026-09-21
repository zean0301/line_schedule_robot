package notify.wooper.service;

import com.google.common.hash.Hashing;
import notify.wooper.mapper.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserLoginService {

	@Autowired
	private final UserLoginMapper userLoginMapper;

	public UserLoginService(UserLoginMapper userLoginMapper) {
		this.userLoginMapper = userLoginMapper;
	}

	public boolean authenticate(String userId, String password) {
		Integer count = userLoginMapper.countByUserIdAndPassword(userId, encodePassword(userId, password));
		return count != null && count > 0;
	}

	private String encodePassword(String userId, String password) {
		String oriString = userId + "-" + password;
		String encodeString = Hashing.sha256().hashString(oriString, StandardCharsets.UTF_8).toString();

		return encodeString.substring(1, encodeString.length() - 1);
	}
}
