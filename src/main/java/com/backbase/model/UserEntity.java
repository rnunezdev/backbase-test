package com.backbase.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@NoArgsConstructor
@Data
public class UserEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String username;
    private String password;
    
    public UserEntity(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
}
