package com.pack.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pack.user.config.JWTTokenGeneratorImpl;
import com.pack.user.exception.UserIdAlreadyExistsException;
import com.pack.user.exception.UserNotFoundException;
import com.pack.user.model.Login;
import com.pack.user.model.User;
import com.pack.user.response.ResponseHandler;
import com.pack.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTTokenGeneratorImpl jwtTokenGenerator;
	
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody User user) throws UserIdAlreadyExistsException {

		return ResponseHandler.generateResponse("User created successfully", HttpStatus.CREATED,
				userService.createUser(user));
	}

	@GetMapping("/viewAll")
	public ResponseEntity<Object> getAllUsers() {

		List<User> list = userService.getAllUsers();
		return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, list);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable int userId) throws UserNotFoundException {

		return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK,
				userService.getUserById(userId));

	}

	@PutMapping("/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody User user) throws UserNotFoundException {

		return ResponseHandler.generateResponse("User updated successfully", HttpStatus.OK,
				userService.updateUser(user));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable int userId) throws UserNotFoundException{
		return ResponseHandler.generateResponse("User deleted successfully", HttpStatus.OK,
				userService.deleteUser(userId));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody Login login) throws UserNotFoundException {
			
//		return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK,
//				userService.loginUser(user.getEmail(), user.getPassword()));
		User user = userService.loginUser(login.getEmail(),login.getPassword());
		return new ResponseEntity<>(jwtTokenGenerator.generateToken(user), HttpStatus.CREATED);
	}

}
