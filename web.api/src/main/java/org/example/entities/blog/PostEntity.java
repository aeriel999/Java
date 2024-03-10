package org.example.entities.blog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String shortDescription;

    @Column(length = 20000, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String meta;

    @Column(length = 200, nullable = false)
    private String urlSlug;

    private boolean published;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime postedOn;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modified;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="blogCategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BlogCategoryEntity blogCategory;

    @OneToMany(mappedBy = "post")
    private List<PostTagMap> tags = new ArrayList<>();;
}
