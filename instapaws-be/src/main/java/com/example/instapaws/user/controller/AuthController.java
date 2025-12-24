package com.example.instapaws.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.instapaws.model.AuthResult;
import com.example.instapaws.user.service.UserService;
import com.example.instapaws.utils.JwtUtil;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
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

		String token = jwtUtil.generateJwtToken(result.getUser());
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@Data
	static class AuthRequest {
		private String username;
		private String password;
	}

	@Data
	static class AuthResponse {
		private final String token;
	}
}
