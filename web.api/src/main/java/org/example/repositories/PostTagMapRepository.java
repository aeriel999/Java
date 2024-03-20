package org.example.repositories;

import org.example.entities.blog.PostTagMap;
import org.example.entities.blog.PostTagPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagMapRepository extends JpaRepository<PostTagMap, PostTagPK> {
}
