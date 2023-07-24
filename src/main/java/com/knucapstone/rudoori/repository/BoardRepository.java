package com.knucapstone.rudoori.repository;

import com.knucapstone.rudoori.model.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findById(Long postId);

    @Query("select p from Posts p where p.title=:title or p.content=:content")
    List<Posts> findByKeywords(@Param("title")String title, @Param("content")String content);
}
