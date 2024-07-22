package com.pack.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.user.exception.UserIdAlreadyExistsException;
import com.pack.user.exception.UserNotFoundException;
import com.pack.user.model.User;
import com.pack.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) throws UserIdAlreadyExistsException {
		if (userRepository.existsById(user.getUserId())) {
			throw new UserIdAlreadyExistsException("User with ID " + user.getUserId() + " already exists.");
		}
		
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(int userId) throws UserNotFoundException {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			return user;
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	public User deleteUser(int userId) throws UserNotFoundException {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			userRepository.delete(user);
			return user;
		} else {
			throw new UserNotFoundException("User not found");
		}

	}

	public User updateUser(User updatedUser) throws UserNotFoundException {

		User user = userRepository.findById(updatedUser.getUserId()).orElse(null);
		if (user != null) {
			user.setName(updatedUser.getName());
			user.setEmail(updatedUser.getEmail());
			user.setPassword(updatedUser.getPassword());
			user.setContactNumber(updatedUser.getContactNumber());
			return userRepository.save(user);

		} else {
			throw new UserNotFoundException("User not found");
		}

	}

	public User loginUser(String email, String password) throws UserNotFoundException {

		User user = userRepository.findByEmailAndPassword(email,password).orElse(null);
		if (user != null) {
			return user;

		} else {
			throw new UserNotFoundException("User not found");
		}

	}

}
