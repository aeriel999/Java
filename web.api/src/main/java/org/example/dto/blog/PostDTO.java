package org.example.dto.blog;

import lombok.Data;
import org.example.entities.blog.PostTagMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private String urlSlug;
    private boolean published;
    private LocalDateTime postedOn;
    private LocalDateTime modified;
    private int categoryId;
    private String categoryName;
    private String categoryUrlSlug;
   private List<TagForShowPostDTO> tags = new ArrayList<>();
}
