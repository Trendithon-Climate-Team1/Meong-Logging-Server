package com.example.climate_backend.domain.like.repository;

import com.example.climate_backend.domain.like.entity.Like;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    int countByPost(Post post);
}
