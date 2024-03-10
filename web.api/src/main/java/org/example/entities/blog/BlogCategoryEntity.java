package org.example.entities.blog;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.shop.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_blog_categories")
public class BlogCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50)
    private String urlSlug;

    @Column(length = 200)
    private String description;

    @OneToMany(mappedBy = "blogCategory")
    private List<PostEntity> posts;
}
