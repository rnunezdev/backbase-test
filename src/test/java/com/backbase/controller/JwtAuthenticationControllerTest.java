package com.backbase.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;

import com.backbase.model.JwtTokenRequest;
import com.backbase.service.impl.JwtUserDetailsService;
import com.backbase.util.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationControllerTest {
	 @Mock
	 private AuthenticationManager authenticationManager;
	 @Mock
	 private JwtTokenUtil jwtTokenUtil;
	 @Mock
	 private JwtUserDetailsService userDetailsService;
	
	
	@InjectMocks
	private JwtAuthenticationController jwtAuthenticationController;
	
	
	
	@Test
	public void testCreateAuthenticationToken() {
		ResponseEntity<?> response = null;
		Mockito.when(jwtTokenUtil
					.generateToken(Mockito.any()))
					.thenReturn("token");
		Mockito.when(userDetailsService
				.loadUserByUsername(Mockito.anyString()))
				.thenReturn(new User("usr", "pwd", new ArrayList<>()));
				
		try {
			response = jwtAuthenticationController.createAuthenticationToken(new JwtTokenRequest("usr","pwd"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
	}
	
	@Test
	public void testCreateAuthenticationTokenBadCredentialsException() {
		Mockito.when(authenticationManager
					.authenticate(Mockito.any()))
					.thenThrow(new BadCredentialsException(""));
				
		Exception exception = assertThrows(Exception.class, () -> {
			jwtAuthenticationController.createAuthenticationToken(new JwtTokenRequest("usr","pwd"));
		});
		Assertions.assertNotNull(exception);
		Assertions.assertEquals(exception.getMessage(), "INVALID_CREDENTIALS");
	}
	
	@Test
	public void testCreateAuthenticationTokenUserDisabledException() {
		Mockito.when(authenticationManager
					.authenticate(Mockito.any()))
					.thenThrow(new DisabledException(""));
				
		Exception exception = assertThrows(Exception.class, () -> {
			jwtAuthenticationController.createAuthenticationToken(new JwtTokenRequest("usr","pwd"));
		});
		Assertions.assertNotNull(exception);
		Assertions.assertEquals(exception.getMessage(), "USER_DISABLED");
	}
	
	

}
