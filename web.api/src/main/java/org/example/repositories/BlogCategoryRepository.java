package org.example.repositories;

import org.example.entities.blog.BlogCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface BlogCategoryRepository extends JpaRepository<BlogCategoryEntity, Integer> {
}
