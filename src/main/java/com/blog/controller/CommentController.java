package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    //constructor based dependency -its advantages, such as immutability and easier testing.
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //For save
    //http://localhost:8080/api/comments?postId=1
   @PostMapping
    public ResponseEntity<?> createComment(
            @RequestParam("postId") long postId,
            @Valid
            @RequestBody CommentDto commentDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto dto = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //for delete
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment is deleted !!", HttpStatus.OK);
    }

    //for Read a/c to post_id
    // http://localhost:8080/api/comments/2
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>>  getCommentByPostId(@PathVariable long postId){
        List<CommentDto> commentDto = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //update
    //http://localhost:8080/api/comments?postId=2
    @PutMapping()
    public ResponseEntity<CommentDto> updateComment(
            @RequestParam("postId") long postId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    //for get all comments and pagisation
    //http://localhost:8080/api/posts?pageNo=0&pageSize=4&sortBy=name&sortDir=asc

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "3",required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        List<CommentDto> dtos = commentService.getAllComments(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }






}
