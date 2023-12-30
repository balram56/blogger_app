package com.blog.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String content;

    //one POst object  is connected to many Comment objects

    //cascade means (Composition) if post is delete then comment is automatically deleted
    //cascade means if the any change in parents then
    //orphanRemoval should be automatically removed from memmory becouse it is true, but not in db
    //post object maped with any comments
    //mappedBy = "post" means that the Post entity is the owner of the relationship, and the association is mapped by the post field in the Comment entity.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
