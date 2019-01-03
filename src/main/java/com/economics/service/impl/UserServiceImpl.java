package com.economics.service.impl;

import com.economics.constant.ErrorMessage;
import com.economics.dto.UserDTO;
import com.economics.entity.User;
import com.economics.exception.UserServiceException;
import com.economics.helper.Utils;
import com.economics.repository.UserRepository;
import com.economics.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
        UserDTO returnValue = new UserDTO();
        User user = repository.findByUserId(userId);
        if(user == null) throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        User userUpdated = repository.save(user);

        BeanUtils.copyProperties(userUpdated, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        User user = repository.findByUserId(userId);
        if(user == null) throw new UserServiceException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage());

        repository.delete(user);
    }

    @Override
    public List<UserDTO> getUsers(int page, int limit) {
        List<UserDTO> listUser = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);

        if(page > 0) page = page -1;

        Page<User> usersPage = repository.findAll(pageableRequest);
        List<User> users = usersPage.getContent();

        users.forEach(user -> {
            UserDTO userDTO = new UserDTO();

            BeanUtils.copyProperties(user, userDTO);
            listUser.add(userDTO);
        });

        return listUser;
    }


}
