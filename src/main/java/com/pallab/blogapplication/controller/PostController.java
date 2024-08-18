package com.pallab.blogapplication.controller;

import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.payloads.PostDto;
import com.pallab.blogapplication.repositories.PostRepo;
import com.pallab.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/{userId}/{categoryId}")
    public Post createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
                           @PathVariable Integer categoryId){
       Post post= this.postService.createPost(postDto,userId,categoryId);

       return post;
    }
}
