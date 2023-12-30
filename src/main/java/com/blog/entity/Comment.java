package com.blog.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String body;
    private String email;
    private String name;

    //Many comment object to one POst object
    @ManyToOne
    @JoinColumn(name = "post_id")//this is froeign key, It helps us join the the different tables
    private Post post;


}
