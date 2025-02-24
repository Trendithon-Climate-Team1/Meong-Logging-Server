package com.example.climate_backend.domain.comment.service;

import com.example.climate_backend.domain.comment.dto.request.CommentReqDto;
import com.example.climate_backend.domain.comment.dto.response.CommentResDto;
import com.example.climate_backend.domain.comment.entity.Comment;
import com.example.climate_backend.domain.comment.repository.CommentRepository;
import com.example.climate_backend.domain.post.entity.Post;
import com.example.climate_backend.domain.post.repository.PostRepository;
import com.example.climate_backend.domain.user.entity.User;
import com.example.climate_backend.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(CommentReqDto commentReqDto) {
        User user = userRepository.findByUserId(commentReqDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다." + commentReqDto.getUserId()));

        Post post = postRepository.findById(commentReqDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + commentReqDto.getPostId()));

        post.increaseComment();

        Comment comment = Comment.builder()
            .comment(commentReqDto.getComment())
            .user(user)
            .post(post)
            .build();

        commentRepository.save(comment);
    }

    public List<CommentResDto> getAllComment(Long postId) {

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + postId));

        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
            .map(CommentResDto::new)
            .collect(Collectors.toList());
    }

    public void updateComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다." + commentId));

        comment.update(comment.getComment());
    }

    public void deleteComment(CommentReqDto commentReqDto, Long commentId) {
        User user = userRepository.findByUserId(commentReqDto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다." + commentReqDto.getUserId()));

        Post post = postRepository.findById(commentReqDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다." + commentReqDto.getPostId()));

        post.decreaseComment();

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다." + commentId));

        commentRepository.delete(comment);
    }
}
