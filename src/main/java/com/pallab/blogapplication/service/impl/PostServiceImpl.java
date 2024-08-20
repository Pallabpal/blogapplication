package com.pallab.blogapplication.service.impl;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.pallab.blogapplication.entities.Category;
import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.entities.User;
import com.pallab.blogapplication.exceptions.ResourceNotFoundException;
import com.pallab.blogapplication.payloads.PostDto;
import com.pallab.blogapplication.payloads.PostResponse;
import com.pallab.blogapplication.repositories.CategoryRepo;
import com.pallab.blogapplication.repositories.PostRepo;
import com.pallab.blogapplication.repositories.UserRepo;
import com.pallab.blogapplication.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PostDto updatePost(PostDto postDto, Integer postId){
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post.setContent(postDto.getContent());

       Post updatedPost = this.postRepo.save(post);

       return this.modelMapper.map(updatedPost,PostDto.class);

    }

    @Override
    public void deletePost(Integer postId){
       Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));
       this.postRepo.delete(post);


    }

    @Override
    public PostDto getPostById(Integer postId){

        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","Id",postId));

     return  this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize){

        Pageable p=  PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post>posts = pagePost.getContent();

//        List<Post>posts=this.postRepo.findAll();
        List<PostDto>postsDto= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postsDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
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
