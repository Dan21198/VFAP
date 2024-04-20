package com.example.oopr3cv9.service;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.repository.NoteRepository;
import com.example.oopr3cv9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public Note saveOrUpdateNote(Note note, User user) {
        note.setUser(user);
        return noteRepository.save(note);
    }

    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId).orElse(null);
    }

    public List<Note> getAllNotes(String userEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Note> userNotes = new ArrayList<>();

        if (isAdmin) {
            userNotes = noteRepository.findAll();
        } else {
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            if (userOptional.isPresent()) {
                User currentUser = userOptional.get();
                userNotes = noteRepository.findAllByUserId(currentUser.getId());
            }
        }
        return userNotes;
    }

    public void deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }


}
