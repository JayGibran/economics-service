package com.economics.controller;


import com.economics.constant.ErrorMessage;
import com.economics.dto.UserDTO;
import com.economics.exception.UserServiceException;
import com.economics.model.UserRequestModel;
import com.economics.model.UserResponseModel;
import com.economics.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/{userId}")
    public UserDTO getUser(@PathVariable String userId){
        return userService.getUserByUserId(userId);
    }

    @PostMapping
    public UserResponseModel createUser(@RequestBody UserRequestModel userRequestModel){
        UserResponseModel userResponseModel = new UserResponseModel();
        UserDTO userDTO = new UserDTO();

        if (userRequestModel.getFirstName() == null) throw new UserServiceException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessage());

        BeanUtils.copyProperties(userRequestModel, userDTO);

        BeanUtils.copyProperties(userService.createUser(userDTO), userResponseModel);

        return userResponseModel;

    }

    @PutMapping(path="/{userId}")
    public UserResponseModel updateUser(@PathVariable String userId, @RequestBody UserRequestModel userRequestModel){
        UserResponseModel userResponseModel = new UserResponseModel();
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userRequestModel, userDTO);

        BeanUtils.copyProperties(userService.updateUser(userId, userDTO), userResponseModel);

        return userResponseModel;
    }

    @DeleteMapping(path="/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<UserResponseModel> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "25") int limit){

        List<UserResponseModel> returnListUser = new ArrayList<UserResponseModel>();

        List<UserDTO> users =  userService.getUsers(page, limit);

        users.forEach(user ->{
            UserResponseModel userResponseModel = new UserResponseModel();
            BeanUtils.copyProperties(user, userResponseModel);
            returnListUser.add(userResponseModel);
        });

        return returnListUser;
    }

}