package com.pallab.blogapplication.controller;

import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.payloads.PostDto;
import com.pallab.blogapplication.repositories.PostRepo;
import com.pallab.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
                           @PathVariable Integer categoryId){
       PostDto createdPost= this.postService.createPost(postDto,userId,categoryId);
       return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);


    }

    //get by user

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = postService.getPostByUser(userId);

        return  new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);

        return  new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }
}
