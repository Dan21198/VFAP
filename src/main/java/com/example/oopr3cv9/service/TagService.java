package com.example.opr3cv9.service;

import com.example.opr3cv9.model.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(Tag tag);

    Tag getTagById(Long id);

    List<Tag> getAllTags();

    void deleteTag(Long id);

    void assignTagToNote(Long tagId, Long noteId);

    void removeTagFromNote(Long tagId, Long noteId);
}
