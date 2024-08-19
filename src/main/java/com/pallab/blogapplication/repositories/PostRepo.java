package com.pallab.blogapplication.repositories;

import com.pallab.blogapplication.entities.Category;
import com.pallab.blogapplication.entities.Post;
import com.pallab.blogapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
