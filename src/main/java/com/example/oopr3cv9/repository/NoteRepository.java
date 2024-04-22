package com.example.oopr3cv9.repository;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long userId);

    List<Note> findAllByUserEmail(String email);

    List<Note> findAllByFinished(boolean finished);

    List<Note> findAllByTagsContains(Tag tag);
}
