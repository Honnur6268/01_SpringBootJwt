package com.security.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;

	private String refreshToken;

}
