package com.pallab.blogapplication.payloads;

import com.pallab.blogapplication.entities.Category;
import com.pallab.blogapplication.entities.Comment;
import com.pallab.blogapplication.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {


    private Integer postId;

    private String title;
    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();




}
