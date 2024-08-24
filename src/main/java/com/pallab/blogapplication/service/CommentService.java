package com.pallab.blogapplication.service;

import com.pallab.blogapplication.payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer PostId);

    void deleteComment(Integer commentId);
}
