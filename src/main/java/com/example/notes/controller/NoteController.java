package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class NoteController {


    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }
    @GetMapping("/new")
    public String newNote(Model model) {
        model.addAttribute("note", new Note());
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        model.addAttribute("note", noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id)));
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute Note note, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty() && isImage(file)) {
            note.setImage(file.getBytes());
        }
        noteRepository.save(note);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/remove-image/{id}")
    public String removeImage(@PathVariable Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id));
        note.setImage(null);
        noteRepository.save(note);
        return "redirect:/edit/" + id;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image");
    }
}