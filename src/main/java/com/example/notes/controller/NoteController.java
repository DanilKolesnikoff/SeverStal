package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("notes", noteRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        model.addAttribute("note", noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid note Id:" + id)));
        return "edit";
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute Note note) {
        noteRepository.save(note);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newNote(Model model) {
        model.addAttribute("note", new Note());
        return "edit";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/uploads/" + file.getOriginalFilename());
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }
}
