package com.sample.dbservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.datapojo.Users;
import com.sample.dbrepo.UserRepo;

@Service
public class DBService {
	@Autowired
	private UserRepo userRepo;

	public Users getByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public Users saveUserEntity(Users myEntity) {
		return userRepo.save(myEntity);
	}
}
