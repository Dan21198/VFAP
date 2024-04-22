package com.example.oopr3cv9.service;

import com.example.oopr3cv9.exception.NotFoundException;
import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.repository.NoteRepository;
import com.example.oopr3cv9.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final AuthenticationService authService;

    public Note saveOrUpdateNote(Note note, User user) {
        note.setUser(user);
        return noteRepository.save(note);
    }

    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(()
                -> new NotFoundException("Note", noteId));
    }

    public List<Note> getAllNotes(String userEmail) {
        List<Note> userNotes = new ArrayList<>();
        if (authService.isAdmin()) {
            userNotes = noteRepository.findAll();
        } else {
            User currentUser = authService.getCurrentUser(userEmail);
            userNotes = noteRepository.findAllByUserId(currentUser.getId());
        }
        return userNotes;
    }

    public List<Note> getNotesByFinishedStatus(boolean finished) {
        return noteRepository.findAllByFinished(finished);
    }

    public List<Note> getNotesByTag(Tag tag) {
        return noteRepository.findAllByTagsContains(tag);
    }

    public void deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }


}
