package com.pack.user.config;

import java.util.Map;

import com.pack.user.model.User;

public interface JWTTokenGenerator {

    Map<String, String> generateToken(User user);
}
