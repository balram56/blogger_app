package com.blog.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users; //duplicate elemenent are not allowed in set , user can not duplicate


}
