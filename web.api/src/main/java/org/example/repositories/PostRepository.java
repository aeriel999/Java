package org.example.repositories;

import org.example.entities.blog.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByPublished(boolean published);

    Page<PostEntity> findByPublished(boolean published, Pageable pageable);

    Page<PostEntity> findByBlogCategory_Id(int categoryId, Pageable pageable);


    Page<PostEntity> findByTags_TagId(int tagId, Pageable pageable);


}
