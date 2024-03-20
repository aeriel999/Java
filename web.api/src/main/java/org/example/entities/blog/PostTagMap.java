package org.example.entities.blog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_postTagMap")
@IdClass(PostTagPK.class)
public class PostTagMap {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", nullable = false)
    private PostEntity post;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="tag_id", nullable = false)
    private TagEntity tag;
}

