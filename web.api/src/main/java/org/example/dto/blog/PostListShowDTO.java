package org.example.dto.blog;

import lombok.Data;
import org.example.entities.blog.PostEntity;

import java.util.List;

@Data
public class PostListShowDTO {

    private List<PostEntity> list;
    private int totalCount;

    public PostListShowDTO(List<PostEntity> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}

