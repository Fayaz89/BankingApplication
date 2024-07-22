package com.pack.user.service;

import java.util.List;

import com.pack.user.exception.UserIdAlreadyExistsException;
import com.pack.user.exception.UserNotFoundException;
import com.pack.user.model.User;

public interface UserService {

	public User createUser(User user) throws UserIdAlreadyExistsException;

	public List<User> getAllUsers();

	public User getUserById(int userId) throws UserNotFoundException;

	public User deleteUser(int userId) throws UserNotFoundException;

	public User updateUser(User updatedUser) throws UserNotFoundException;
	
	public User loginUser(String email, String password) throws UserNotFoundException;

}
