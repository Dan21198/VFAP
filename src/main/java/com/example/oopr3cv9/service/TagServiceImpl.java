package com.example.opr3cv9.service;

import com.example.opr3cv9.exception.NotFoundException;
import com.example.opr3cv9.model.Note;
import com.example.opr3cv9.model.Tag;
import com.example.opr3cv9.repository.NoteRepository;
import com.example.opr3cv9.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id: " + id));
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }

    @Override
    public void assignTagToNote(Long tagId, Long noteId) {
        Tag tag = getTagById(tagId);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found with id: " + noteId));

        note.getTags().add(tag);
        noteRepository.save(note);
    }

    @Override
    public void removeTagFromNote(Long tagId, Long noteId) {
        Tag tag = getTagById(tagId);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found with id: " + noteId));

        note.getTags().remove(tag);
        noteRepository.save(note);
    }
}
