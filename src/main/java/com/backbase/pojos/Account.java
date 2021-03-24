package com.backbase.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
	private String id;
	private Holder holder;
	private String number;
	private String kind;
	private Metadata metadata;
	

}
