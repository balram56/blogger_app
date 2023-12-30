package com.blog.service.impl;


import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;//object is created
    public PostServiceImpl(PostRepository postRepo) {//two ways of dependency injection 1.@Autowired, 2.Constuctor based dependency injection1
        this.postRepo = postRepo;
    }
    @Override
    public PostDto createPost(PostDto postDto) {


        //for saving database , service Layer to db

        Post post = new Post();
        //this is post content before saving

         post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        //save to required repository layer
        Post savedPost = postRepo.save(post);
        //this is post content after saving
        PostDto dto = new PostDto();
        dto.setId(savedPost.getId());
        dto.setTitle(savedPost.getTitle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());
        //dto.setMessage("post is created");
        return dto;
    }
//For Delete nd  throw the  exception
    @Override
    public void deletePost(long id) {
//        Optional<Post> byId = postRepo.findById(id);//its is not good manner write to code
//        if(byId.isPresent()){
//            postRepo.deleteById(id);
//        }else{
//            throw new ResourceNotFoundException("post not found with id" +id);//throw the exception only
//        }

        //it is good way for customized exception nd Handle
        Post post = postRepo.findById(id).orElseThrow(
                ()->new RuntimeException("Post not found with id :" +id)//used lambda expression
        );//if it throw the exception nd stop here , if it doesnot throw the exception delete the record
        //this like try block here only throw the exception
        postRepo.deleteById(id);

    }

    //For read, pageable, sort , direction table ac to page
    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {//read data
      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
              :Sort.by(sortBy).descending(); //this is used direction for sort
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
       // Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));//Sort.by() methos is automatically convert String to sort Object
      Page<Post> pagePosts = postRepo.findAll(pageable);//findAll(Pageable)
        List<Post> posts = pagePosts.getContent();//getContent() -convert all the page post to list of post
        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dtos;
    }


    //for update
    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not Found with id :" + postId)
        );
        //for update in db
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        //convert object from post to postdto
        Post savePost = postRepo.save(post);
        PostDto dto = mapToDto(savePost);
        return dto;
    }

    PostDto mapToDto(Post post){//this method should copy all the data from the post(entity object) to dto
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
}
