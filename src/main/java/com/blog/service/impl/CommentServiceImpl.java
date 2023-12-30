package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id:" + postId)
        );
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);//Setting a comment for a particular post or seting more than one comment a particular post


        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = new CommentDto();
        dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setBody(saveComment.getBody());
        dto.setEmail(saveComment.getEmail());

        return dto;
    }

    //delete the comments
    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment is not found with id:" + commentId)
        );
        commentRepository.deleteById(commentId);
        
    }
    //when post is deleted then comment is automatically delete in db ,
//    @Override
//    public void deletePostAndComment(long commentId) {
//        Post post = postRepository.findById(commentId).orElseThrow(
//                () -> new ResourceNotFoundException("Comment is not found with id:" + commentId)
//        );
//        postRepository.deleteById(commentId);
//
//    }
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Comment is not availabe with id :" + postId)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment saveComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(saveComment);


        return dto;

    }

    @Override
    public List<CommentDto> getAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
       Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Comment> pageComments = commentRepository.findAll(pageable);
        List<Comment> comments = pageComments.getContent();

        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        return dtos;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
