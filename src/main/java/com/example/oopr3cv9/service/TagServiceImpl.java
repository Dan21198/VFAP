package com.example.oopr3cv9.service;


import com.example.oopr3cv9.exception.NotFoundException;
import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.repository.NoteRepository;
import com.example.oopr3cv9.repository.TagRepository;
import com.example.oopr3cv9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

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
    public List<Tag> getAllTags(String userEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        List<Tag> userTags = new ArrayList<>();

        if (isAdmin) {
            userTags = tagRepository.findAll();
        } else {
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            if (userOptional.isPresent()) {
                User currentUser = userOptional.get();
                userTags = tagRepository.findAllByUserId(currentUser.getId());
            }
        }
        return userTags;
    }

    public List<Tag> getTagsByNoteId(Long noteId) {
        return tagRepository.findAllByNotesId(noteId);
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
