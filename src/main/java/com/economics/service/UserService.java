package com.economics.service;

import com.economics.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(String email);

    UserDTO getUserByUserId(String userId);
}
