package com.pallab.blogapplication.controller;


import com.pallab.blogapplication.payloads.ApiResponse;
import com.pallab.blogapplication.payloads.CommentDto;
import com.pallab.blogapplication.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId){

        CommentDto savedComment = this.commentService.createComment(commentDto, postId);
        return  new ResponseEntity<CommentDto>(savedComment, HttpStatus.OK);

    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?>  deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<>(new ApiResponse("deleted successfully",true),HttpStatus.OK);
    }
}
