package com.example.climate_backend.domain.comment.repository;

import com.example.climate_backend.domain.comment.entity.Comment;
import com.example.climate_backend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

}
