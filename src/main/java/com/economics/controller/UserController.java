package com.economics.controller;


import com.economics.constant.ErrorMessage;
import com.economics.exceptions.UserServiceException;
import com.economics.dto.model.request.UserDetailsRequestModel;
import com.economics.dto.model.response.UserResponse;
import com.economics.service.UserService;
import com.economics.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/{userId}")
    public UserResponse getUser(@PathVariable String userId){
        UserResponse userResponse = new UserResponse();
        UserDTO userDTO = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDTO, userResponse);
        return userResponse;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserResponse returnValue = new UserResponse();
        UserDTO userDTO = new UserDTO();

        if (userDetails.getFirstName() == null) throw new UserServiceException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessage());

        BeanUtils.copyProperties(userDetails, userDTO);

        UserDTO createdUser = userService.createUser(userDTO);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }


    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }

}