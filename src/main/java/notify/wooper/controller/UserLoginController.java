package notify.wooper.controller;

import lombok.extern.slf4j.Slf4j;
import notify.wooper.dto.UserLoginRequest;
import notify.wooper.dto.UserLoginResponse;
import notify.wooper.service.UserLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserLoginController {

	private final UserLoginService userLoginService;

	public UserLoginController(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	@PostMapping("/user_login")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
		if (request.userId() == null || request.userId().isBlank() || request.password() == null || request.password().isBlank()) {
			return ResponseEntity.badRequest().body(new UserLoginResponse(false, "user_id and password are required", ""));
		}

		log.info("/user_login start");
		log.debug("/user_login user: {}", request.userId());

		String authenticated = userLoginService.authenticate(request.userId(), request.password());

		log.info("/user_login end");
		return ResponseEntity.ok(new UserLoginResponse(true, "Login success", authenticated));
	}
}
