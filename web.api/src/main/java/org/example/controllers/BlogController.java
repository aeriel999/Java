package org.example.controllers;


import lombok.AllArgsConstructor;
import org.example.dto.blog.PostDTO;
import org.example.dto.blog.PostListShowDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductSearchResultDTO;
import org.example.entities.blog.PostEntity;
import org.example.services.BlogService;
import org.example.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/blog")
public class BlogController {
    private final BlogService blogService;
    @GetMapping
    public ResponseEntity<List<PostDTO>> index() {
        var res = blogService.get();
        if (res == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @GetMapping("/posts")
//    public ResponseEntity<PostListShowDTO> showPosts(
//            @RequestParam(defaultValue = "") String name,
//            @RequestParam(defaultValue = "0") int categoryId,
//            @RequestParam(defaultValue = "") String description,
//            @RequestParam(defaultValue = "") String tag,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//        PostListShowDTO searchResult = productService.searchProducts(name, categoryId,
//                description, page, size);
//        return new ResponseEntity<>(searchResult, HttpStatus.OK);
//    }


}
