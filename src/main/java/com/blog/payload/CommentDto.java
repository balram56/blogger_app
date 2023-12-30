package com.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 4, message = "name should be 4 characters minimum")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min =10, message = "body should be 10 characters minimum")
    private String body;

}
