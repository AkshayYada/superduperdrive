package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final NotesService notesService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, NotesService notesService, CredentialService credentialService) {
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialService = credentialService;

    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model)
    {
        model.addAttribute("files", fileService.getAllFiles(authentication.getName()));
        model.addAttribute("notes",notesService.getAllNotes(authentication.getName()));
        model.addAttribute("credentials",credentialService.getCredential(authentication.getName()));
        return "home";
    }


}
