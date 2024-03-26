package org.example.repositories;

import org.example.entities.blog.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    TagEntity findById(long id);
}
