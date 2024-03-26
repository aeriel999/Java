package org.example.repositories;

import org.example.entities.blog.PostEntity;
import org.example.entities.blog.PostTagMap;
import org.example.entities.blog.PostTagPK;
import org.example.entities.blog.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostTagMapRepository extends JpaRepository<PostTagMap, PostTagPK> {
    @Query("SELECT pt.tag.id FROM PostTagMap pt WHERE pt.post.id = :postId")
    List<Integer> findTagIdsByPostId(@Param("postId") Integer postId);
}
