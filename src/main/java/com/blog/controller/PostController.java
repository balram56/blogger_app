package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController//for api
@RequestMapping("/api/posts")
public class  PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

//For create data
    //ResponseEntity<String> is show the response on postman (views) post is created
    //ResponseEntity<PostDto> is return back all things retunrn back in present  Dto
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping //when ever u want to jason object to database used postmapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){//data is comes from jason via url, this jason  will  go to the controller layer ,
        //and copy data from jason to Postdto using @RequestBody annotation is required
//Create data on db
      //  <?> its means dynamically replaced which keyword is return
        if (bindingResult.hasErrors()){//if error is true then back to postman nd false then go to service layer

            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//this send default message on postman
        }
       PostDto dto = postService.createPost(postDto);

return new ResponseEntity<>(dto, HttpStatus.CREATED);//return on postman "post is created"
    }

    //for delete
    @PreAuthorize("hasRole('ADMIN')")// only can  admin create the post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted!!", HttpStatus.OK);
    }

    //For READ the data from data base ,pageable, sort, direction

    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
    // how did read this linnk data , its read data using @RequestParam
    //sortBy=title - sort title a/c alphabetical

    //sortBy=desc - sort by descending order for direction
 @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "3",required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir

    ){

        List<PostDto> postDtos = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);

    }
    // For Update by id
    //http://localhost:8080/api/posts?postId=1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<PostDto> updatePost(
        @RequestParam("postId") long postId,//postId data come from jason object
                @RequestBody PostDto postDto
    ){
        PostDto dto = postService.updatePost(postId, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
