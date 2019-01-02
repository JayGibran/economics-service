package com.economics.service.impl;

import com.economics.model.User;
import com.economics.repository.UserRepository;
import com.economics.service.UserService;
import com.economics.helper.Utils;
import com.economics.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if(repository.findByEmail(userDTO.getEmail()) != null ) throw new RuntimeException("Record already exist");

        User userEntity = new User();
        BeanUtils.copyProperties(userDTO, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));

        User storedUserDetails = repository.save(userEntity);

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDTO getUser(String email) {
        User user = repository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO userDTO = new UserDTO();
        User user = repository.findByUserId(userId);
        if (user == null) throw new UsernameNotFoundException(userId);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
