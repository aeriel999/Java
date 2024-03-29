package org.example.dto.blog;

import lombok.Data;

import java.util.List;

@Data
public class PostListByTag {
    private List<PostDTO> list;
    private int totalCount;
    private String tagName;
    private String tagDescription;



    public PostListByTag(List<PostDTO> list, int totalCount, String tagName, String tagDescription) {
        this.list = list;
        this.totalCount = totalCount;
        this.tagName = tagName;
        this.tagDescription = tagDescription;

    }
}
