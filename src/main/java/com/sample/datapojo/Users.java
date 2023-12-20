package com.sample.datapojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection = "users")
public class Users {
	@Id
	String username;
	String hashPass;
	int age;
}
