package com.example.opr3cv9.service;

import com.example.opr3cv9.model.Note;

import java.util.List;

public interface NoteService {
    Note saveOrUpdateNote(Note note);

    Note getNoteById(Long noteId);

    List<Note> getAllNotes();

    void deleteNoteById(Long noteId);

}
