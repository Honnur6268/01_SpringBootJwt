package com.security.dto;

import lombok.Data;

@Data
public class SignupRequest {

//	private Integer id;

	private String firstname;

	private String lastname;

	private String email;

	private String password;

}
