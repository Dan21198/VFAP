package com.example.oopr3cv9.repository;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT DISTINCT t FROM Tag t LEFT JOIN t.notes n WHERE n.user.id = :userId OR n IS NULL")
    List<Tag> findAllByUserId(@Param("userId") Long userId);

    List<Tag> findAllByNotesId(Long noteId);

}
