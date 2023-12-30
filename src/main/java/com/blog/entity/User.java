package com.blog.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(   //join two tables for used, create third table
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id" ,referencedColumnName = "id"),
            //primary key in user table is userid nd foreign key in third tables
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;//user is not duplicate i.e used set
}

