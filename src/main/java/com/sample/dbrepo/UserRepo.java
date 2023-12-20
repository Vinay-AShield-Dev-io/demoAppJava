package com.sample.dbrepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sample.datapojo.Users;
import java.lang.String;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {
	public Users findByUsername(String username);
}
