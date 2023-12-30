package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto updateComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllComments(int pageNo, int pageSize, String sortBy, String sortDir);
}
