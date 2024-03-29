package org.example.dto.blog;

import lombok.Data;

import java.util.List;
@Data
public class PostListByCategoryDTO {
    private List<PostDTO> list;
    private int totalCount;
    private String categoryName;
    private String categoryDescription;



    public PostListByCategoryDTO(List<PostDTO> list, int totalCount, String categoryName, String categoryDescription) {
        this.list = list;
        this.totalCount = totalCount;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;

    }
}
