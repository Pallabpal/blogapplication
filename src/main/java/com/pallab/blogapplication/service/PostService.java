package com.pallab.blogapplication.service;

import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.payloads.PostDto;

import java.util.List;

public interface PostService {

    Post createPost(PostDto postDto, Integer userId, Integer categoryId);
    Post updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    List<Post> getAllPost();
    Post getPostById(Integer postId);

    List<Post> getPostByCategory(Integer categoryId);

    List<Post> getPostByUser(Integer postId);

    List<Post> searchPosts(String keyword);
}
