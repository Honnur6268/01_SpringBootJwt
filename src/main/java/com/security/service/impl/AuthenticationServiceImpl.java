package com.security.service.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.JwtAuthResponse;
import com.security.dto.RefreshTokenRequest;
import com.security.dto.SignInRequest;
import com.security.dto.SignupRequest;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.repository.UserRepository;
import com.security.service.AuthenticationService;
import com.security.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JWTService jwtService;

	public User signUp(SignupRequest request) {
		User user = new User();

//		user.setId(request.getId());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		return repository.save(user);
	}

	public JwtAuthResponse signIn(SignInRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var user = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(jwt);
		authResponse.setRefreshToken(refreshToken);

		return authResponse;
	}
	
	public JwtAuthResponse serviceAuthenticationFoThirdPartyApps(SignInRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var user = repository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Unable to authenticate, Invalid email or password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(jwt);
		authResponse.setRefreshToken(refreshToken);

		return authResponse;
	}

	public JwtAuthResponse refreshToken(RefreshTokenRequest request) {
		String userEmail = jwtService.extarctUsername(request.getToken());
		User user = repository.findByEmail(userEmail).orElseThrow();
		if (jwtService.isTokenValid(request.getToken(), user)) {
			var token = jwtService.generateToken(user);
			JwtAuthResponse authResponse = new JwtAuthResponse();
			authResponse.setToken(token);
			authResponse.setRefreshToken(request.getToken());

			return authResponse;
		}
		return null;
	}
}
