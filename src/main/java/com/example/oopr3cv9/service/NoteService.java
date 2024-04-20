package com.example.oopr3cv9.service;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.User;

import java.util.List;

public interface NoteService {
    Note saveOrUpdateNote(Note note, User user);

    Note getNoteById(Long noteId);

    List<Note> getAllNotes(String email);

    void deleteNoteById(Long noteId);

}
