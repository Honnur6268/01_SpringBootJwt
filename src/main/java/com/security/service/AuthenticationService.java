package com.security.service;

import com.security.dto.JwtAuthResponse;
import com.security.dto.RefreshTokenRequest;
import com.security.dto.SignInRequest;
import com.security.dto.SignupRequest;
import com.security.entity.User;

public interface AuthenticationService {

	public User signUp(SignupRequest request);

	public JwtAuthResponse signIn(SignInRequest request);
	
	public JwtAuthResponse serviceAuthenticationFoThirdPartyApps(SignInRequest request);

	JwtAuthResponse refreshToken(RefreshTokenRequest request);
}
