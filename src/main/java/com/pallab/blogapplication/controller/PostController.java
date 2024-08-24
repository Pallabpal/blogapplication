package com.pallab.blogapplication.controller;


import com.pallab.blogapplication.config.AppConstants;
import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.payloads.ApiResponse;
import com.pallab.blogapplication.payloads.PostDto;
import com.pallab.blogapplication.payloads.PostResponse;
import com.pallab.blogapplication.service.FileService;
import com.pallab.blogapplication.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);

        return  new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);

    }

    //get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false)  String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
            ){

        PostResponse posts = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto posts = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(posts,HttpStatus.OK);
    }


    @DeleteMapping("/post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("deleted successfully", true);

    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId){
        PostDto updatePost= this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPosts(
            @PathVariable("keywords") String keywords
    ){
        List<PostDto>result =this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

    }

    //image upload

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(@PathVariable Integer postId,
                                                        @RequestParam("image")MultipartFile image) throws IOException {

        PostDto postdto = this.postService.getPostById(postId);
        String fileName= this.fileService.uploadImage(path, image);
        postdto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postdto,postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);


    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable ("imageName") String imageName, HttpServletResponse response
    ) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }




}
