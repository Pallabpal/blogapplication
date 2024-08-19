package com.pallab.blogapplication.service.impl;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.pallab.blogapplication.entities.Category;
import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.entities.User;
import com.pallab.blogapplication.exceptions.ResourceNotFoundException;
import com.pallab.blogapplication.payloads.PostDto;
import com.pallab.blogapplication.repositories.CategoryRepo;
import com.pallab.blogapplication.repositories.PostRepo;
import com.pallab.blogapplication.repositories.UserRepo;
import com.pallab.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId){

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));


        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));


        Post post = modelMapper.map(postDto, Post.class);


        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost= this.postRepo.save(post);


        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId){
        return  null;
    }

    @Override
    public void deletePost(Integer postId){

    }

    @Override
    public Post getPostById(Integer postId){
     return  null;
    }

    @Override
    public List<Post> getAllPost(){
        return  null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryID){

        Category category= this.categoryRepo.findById(categoryID).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryID));
        List<Post> posts= this.postRepo.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId){
         User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
         List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<Post> searchPosts(String keyword){
        return null;
    }




}
