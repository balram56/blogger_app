package com.blog.repository;

import com.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface CommentRepository extends JpaRepository<Comment, Long> {

    //for read
    List<Comment> findByPostId(long postId);//templet is method name like findByPostId()


}
