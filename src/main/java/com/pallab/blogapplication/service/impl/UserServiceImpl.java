package com.pallab.blogapplication.service.impl;

import com.pallab.blogapplication.entities.User;
import com.pallab.blogapplication.exceptions.ResourceNotFoundException;
import com.pallab.blogapplication.payloads.UserDto;
import com.pallab.blogapplication.repositories.UserRepo;
import com.pallab.blogapplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser =this.userRepo.save(dtoToUser(userDto));
        return this.userToDto(savedUser);

    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId){

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser =this.userRepo.save(user);
        return this.userToDto(savedUser);


    }
    @Override
   public UserDto getUserById(Integer userId){

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

        return this.userToDto(user);



    }

    public List<UserDto> getAllUsers(){

        List<User> users = this.userRepo.findAll();

       List<UserDto> userDto= users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());

        return  userDto;
    }
    public  void deleteUser(Integer userId){

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);



    }

    public User dtoToUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
          User user = this. modelMapper.map(userDto, User.class);
        return user;

    }
    public UserDto userToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        UserDto userDto = this. modelMapper.map(user, UserDto.class);

        return userDto;

    }
}
