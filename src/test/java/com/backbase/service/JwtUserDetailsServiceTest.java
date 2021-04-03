package com.backbase.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.backbase.model.UserEntity;
import com.backbase.repositories.UserRepository;
import com.backbase.service.impl.JwtUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

	@Mock
	private UserRepository repo;

	@InjectMocks
	JwtUserDetailsService jwtUserDetailsService;

	@Test
	void loadUserByUsernameTest() {

		Mockito.when(repo.findByUsername(Mockito.anyString())).thenReturn(new UserEntity("usr", "pwd"));

		UserDetails user = jwtUserDetailsService.loadUserByUsername("user");

		Assertions.assertNotNull(user);
		Assertions.assertEquals(user.getUsername(), "usr");
		Assertions.assertEquals(user.getPassword(), "pwd");
	}

	@Test
	void loadUserByUsernameTestException() {

		Mockito.when(repo.findByUsername(Mockito.anyString())).thenReturn(null);

		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
			jwtUserDetailsService.loadUserByUsername("user");
		});
		Assertions.assertNotNull(exception);
		Assertions.assertEquals(exception.getMessage(), "User not found with username: user");
	}

}
