package com.security.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.JwtAuthResponse;
import com.security.dto.RefreshTokenRequest;
import com.security.dto.SignInRequest;
import com.security.dto.SignupRequest;
import com.security.entity.User;
import com.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody SignupRequest request) {
		return ResponseEntity.ok(authenticationService.signUp(request));
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignInRequest request) {
		return ResponseEntity.ok(authenticationService.signIn(request));
	}
	
	@PostMapping("/third_party_authentication")
	public ResponseEntity<JwtAuthResponse> authenticateServiceProvider(@RequestBody SignInRequest request) {
		return ResponseEntity.ok(authenticationService.serviceAuthenticationFoThirdPartyApps(request));
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authenticationService.refreshToken(request));
	}
}
