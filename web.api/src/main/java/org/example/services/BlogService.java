package org.example.services;

import org.example.dto.blog.PostDTO;
import org.example.entities.blog.PostEntity;
import java.util.List;

public interface BlogService {
    List<PostDTO> get();
}
