package com.backbase.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring-web-servlet-config.xml"})
public class JwtTokenUtilTest {
	
	@Autowired
	ApplicationContext context;
	
	
	@Test
	public void generateTokenTest() {
		JwtTokenUtil util = context.getBean(JwtTokenUtil.class);
		UserDetails user = new User("rnunez", "rnunezpwd", new ArrayList<>());
		
		String token = util.generateToken(user);
		
		Assertions.assertNotNull(token);
		
	}
	
	@Test
	public void validateTokenTest() {
		JwtTokenUtil util = context.getBean(JwtTokenUtil.class);
		UserDetails user = new User("rnunez", "rnunezpwd", new ArrayList<>());
		
		String token = util.generateToken(user);
		
		Boolean isValidToken = util.validateToken(token, user);
		
		Assertions.assertTrue(isValidToken);
		
	}
	
	@Test
	public void getUsernameFromTokenTest() {
		JwtTokenUtil util = context.getBean(JwtTokenUtil.class);
		UserDetails user = new User("rnunez", "rnunezpwd", new ArrayList<>());
		
		String token = util.generateToken(user);
		
		String username = util.getUsernameFromToken(token);
		
		Assertions.assertEquals(username, "rnunez");
		
	}
	
	@Test
	public void getExpirationDateFromTokenTest() {
		LocalDateTime now = LocalDateTime.now();
		JwtTokenUtil util = context.getBean(JwtTokenUtil.class);
		UserDetails user = new User("rnunez", "rnunezpwd", new ArrayList<>());
		
		String token = util.generateToken(user);
		
		Date date = util.getExpirationDateFromToken(token);
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
		
		Assertions.assertNotNull(date);
		Assertions.assertEquals(ldt.getHour(), now.getHour()+5);
		
	}

}
