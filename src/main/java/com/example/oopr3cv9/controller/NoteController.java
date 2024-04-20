package com.example.oopr3cv9.controller;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Note note = noteService.getNoteById(noteId);

        if (note != null && note.getUser().getUsername().equals(username)) {
            return new ResponseEntity<>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<List<Note>> getAllNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String currentUserId = userDetails.getUsername();

        List<Note> notes = noteService.getAllNotes(currentUserId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Note> createNote(@RequestBody Note note, @AuthenticationPrincipal User user) {
        Note createdNote = noteService.saveOrUpdateNote(note, user);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @PutMapping("/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId,
                                           @RequestBody Note note,
                                           @AuthenticationPrincipal User user) {

        Note existingNote = noteService.getNoteById(noteId);
        if (existingNote == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (existingNote.getUser().getUsername().equals(user.getUsername())) {
            note.setId(noteId);
            Note updatedNote = noteService.saveOrUpdateNote(note, user);
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @DeleteMapping("/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Note note = noteService.getNoteById(noteId);

        if (note != null && note.getUser().getUsername().equals(username)) {
            noteService.deleteNoteById(noteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}
