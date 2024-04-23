package com.example.oopr3cv9.controller;

import com.example.oopr3cv9.model.Note;
import com.example.oopr3cv9.model.Tag;
import com.example.oopr3cv9.model.TagDto;
import com.example.oopr3cv9.model.User;
import com.example.oopr3cv9.repository.TagRepository;
import com.example.oopr3cv9.repository.UserRepository;
import com.example.oopr3cv9.service.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TagController {
    private final TagServiceImpl tagService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Tag> createTag(@RequestBody TagDto tagDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        tag.setUser(user);
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Tag>> getAllTags(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        List<Tag> tags = tagService.getAllTags(userEmail);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/notes/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Tag>> getTagsByNoteId(@PathVariable Long noteId) {
        List<Tag> tags = tagService.getTagsByNoteId(noteId);
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{tagId}/assign/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> assignTagToNote(
            @PathVariable Long tagId,
            @PathVariable Long noteId
    ) {
        tagService.assignTagToNote(tagId, noteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{tagId}/remove/{noteId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
    public ResponseEntity<Void> removeTagFromNote(
            @PathVariable Long tagId,
            @PathVariable Long noteId
    ) {
        tagService.removeTagFromNote(tagId, noteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
