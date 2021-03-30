package com.backbase.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtTokenResponse implements Serializable {
	private static final long serialVersionUID = 3482203249851242249L;
	private final String jwtToken;
	

}
