package com.blog.payload;


import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;//dto nd entity class variable are same
    @NotEmpty //improt from spring validation, not null and not empty
   @Size(min = 2, message = "Title should be atleas 2 characters")//size is apply only one String
    private String title;
    @NotEmpty
    @Size(min = 2, message = "Description should be atleast 4 characters")//if u not wite message then gives default message
    private String description;

    @NotEmpty
    @Size(min = 2, message = "Content should be atleast 4 characters")
    private String content;
    //private String message;


}
