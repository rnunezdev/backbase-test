package com.backbase.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenRequest implements Serializable {
	private static final long serialVersionUID = 502593362163929418L;
	private String username;
    private String password;
}
