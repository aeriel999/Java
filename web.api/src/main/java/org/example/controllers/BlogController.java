package org.example.controllers;


import lombok.AllArgsConstructor;
import org.example.dto.blog.PostDTO;
import org.example.dto.blog.PostListByCategoryDTO;
import org.example.dto.blog.PostListByTag;
import org.example.dto.blog.PostListShowDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductSearchResultDTO;
import org.example.entities.blog.PostEntity;
import org.example.services.BlogService;
import org.example.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/blog")
public class BlogController {
    private final BlogService blogService;
    @GetMapping
    public ResponseEntity<PostListShowDTO> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<PostDTO> postPage = blogService.getPostsByPage(page, pageSize);
        List<PostDTO> posts = postPage.getContent();
        int totalCount = (int) postPage.getTotalElements();
        PostListShowDTO postListShowDTO = new PostListShowDTO(posts, totalCount);
        return ResponseEntity.ok().body(postListShowDTO);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId) {
        return blogService.getPostById(postId)
                .map(post -> ResponseEntity.ok().body(post))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category")
    public ResponseEntity<PostListByCategoryDTO> getListPostByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) int categoryId) {

        PostListByCategoryDTO postListByCategoryDTO = blogService.getPostsByCategoryId(categoryId, page, pageSize);

        return ResponseEntity.ok().body(postListByCategoryDTO);
    }

    @GetMapping("/tag")
    public ResponseEntity<PostListByTag> getListPostByTag(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) int tagId) {

        PostListByTag postListByTag = blogService.getPostsByTagId(tagId, page, pageSize);

        return ResponseEntity.ok(postListByTag);
    }

}
