package com.example.climate_backend.domain.comment.controller;

import com.example.climate_backend.domain.comment.dto.request.CommentReqDto;
import com.example.climate_backend.domain.comment.dto.response.CommentResDto;
import com.example.climate_backend.domain.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentReqDto commentReqDto) {
        commentService.createComment(commentReqDto);
        return ResponseEntity.ok("댓글이 등록되었습니다");
    }

    @GetMapping
    public ResponseEntity<List<CommentResDto>> getAllComment(@RequestBody CommentReqDto commentReqDto) {
        return ResponseEntity.ok(commentService.getAllComment(commentReqDto));
    }

    @PutMapping
    public ResponseEntity<String> updateComment(@RequestBody Long commentId) {
        commentService.updateComment(commentId);
        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestBody CommentReqDto commentReqDto, Long commentId) {
        commentService.deleteComment(commentReqDto, commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다.");
    }
}
