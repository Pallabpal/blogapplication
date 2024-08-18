package com.pallab.blogapplication.repositories;

import com.pallab.blogapplication.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {


}
