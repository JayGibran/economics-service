package com.economics.service;

import com.economics.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(String email);

    UserDTO getUserByUserId(String userId);

    UserDTO updateUser(String userId, UserDTO userDTO);

    void deleteUser(String userId);

    List<UserDTO> getUsers(int page, int limit);
}
