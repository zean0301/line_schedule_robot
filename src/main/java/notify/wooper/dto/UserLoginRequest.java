package notify.wooper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLoginRequest(
	@JsonProperty("user_id") String userId,
	String password
) {
}
