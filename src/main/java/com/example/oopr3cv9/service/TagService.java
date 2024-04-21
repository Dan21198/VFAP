package com.example.oopr3cv9.service;



import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import com.example.oopr3cv9.model.User;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag createTag(Tag tag);

    Tag getTagById(Long id);

    List<Tag> getAllTags(String userEmail);

    List<Tag> getTagsByNoteId(Long noteId);

    void deleteTag(Long id);

    void assignTagToNote(Long tagId, Long noteId);

    void removeTagFromNote(Long tagId, Long noteId);
}
