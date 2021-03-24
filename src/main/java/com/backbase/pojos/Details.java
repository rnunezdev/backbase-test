package com.backbase.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {
	private Value value;
	private String type;
	private String description;

}
