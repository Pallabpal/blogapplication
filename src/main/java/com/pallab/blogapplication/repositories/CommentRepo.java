package com.pallab.blogapplication.repositories;

import com.pallab.blogapplication.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
