package com.pallab.blogapplication.controller;

import com.pallab.blogapplication.payloads.ApiResponse;
import com.pallab.blogapplication.payloads.UserDto;
import com.pallab.blogapplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto){
       UserDto createdUserDto= this.userService.createUser(userDto);
       return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);

    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId){
        UserDto updateUserDto =this.userService.updateUser(userDto,userId);

        return ResponseEntity.ok(updateUserDto);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser( @PathVariable int userId){
        this.userService.deleteUser(userId);
//        return ResponseEntity.ok(Map.of("message","delete successful"));

        return new ResponseEntity<>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>findUserById(@PathVariable Integer userId){
        UserDto userDto= userService.getUserById(userId);
        return ResponseEntity.ok(userDto);

    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>>findAllUsers(){
        List<UserDto> users= userService.getAllUsers();
        return ResponseEntity.ok(users);

    }

}
