package org.example.services;

import org.example.dto.blog.PostDTO;
import org.example.dto.blog.PostListByCategoryDTO;
import org.example.dto.blog.PostListByTag;
import org.example.entities.blog.PostEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Page<PostDTO> getPostsByPage(int page, int pageSize);
    PostListByCategoryDTO getPostsByCategoryId(int categoryId, int page, int pageSize);
    PostListByTag getPostsByTagId(int tagId, int page, int pageSize);
    Optional<PostDTO> getPostById(int postId);
}
