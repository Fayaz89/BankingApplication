package com.pack.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pack.user.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer>{
	
	Optional<User> findByEmailAndPassword(String email,String password);

}
