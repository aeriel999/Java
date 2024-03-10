package org.example.entities.blog;

import lombok.Data;

@Data
public class PostTagPK {
    private PostEntity post;
    private TagEntity tag;
}
