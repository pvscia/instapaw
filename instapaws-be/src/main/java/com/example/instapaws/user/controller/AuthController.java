package com.example.instapaws.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.instapaws.model.AuthResult;
import com.example.instapaws.model.User;
import com.example.instapaws.user.service.AuthService;
import com.example.instapaws.utils.JwtUtil;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService userService;
	private final JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest request) {

		AuthResult result = userService.registerUser(request.getUsername(), request.getPassword());

		if (!result.isSuccess()) {
			return ResponseEntity.badRequest().body(result.getMessage());
		}

		return ResponseEntity.ok(result.getMessage());
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {

		AuthResult result = userService.login(request.getUsername(), request.getPassword());

		if (!result.isSuccess()) {
			return ResponseEntity.status(401).body(result.getMessage());
		}

		User user = result.getUser();
		user.setPassword(null);
		String token = jwtUtil.generateJwtToken(user);
		return ResponseEntity.ok(new AuthResponse(token,user));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getUser(
	        @RequestHeader("Authorization") String authHeader
	) {
	    String token = authHeader.replace("Bearer ", "");

	    Long id = jwtUtil.getIdFromJwtToken(token);

	    AuthResult result = userService.getUser(id);
	    if (!result.isSuccess()) {
	        return ResponseEntity.status(401).body(result.getMessage());
	    }

	    return ResponseEntity.ok(result.getUser());
	}


	@Data
	static class AuthRequest {
		private String username;
		private String password;
	}

	@Data
	static class AuthResponse {
		private final String token;
		private final User user;
	}
}
