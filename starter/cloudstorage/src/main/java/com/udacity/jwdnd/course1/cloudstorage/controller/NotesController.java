package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/notes")
    public String addNotes(Authentication authentication, Notes notes, Model model)
    {
        String userName = authentication.getName();
       int row = notesService.addNotesAndDescription(notes ,userName);

        if(row > 0)
        {

            model.addAttribute("Success",true);
        }
        else
        {
            model.addAttribute("Unsuccess","error");
        }

        return "result";
    }

    @GetMapping("/notes/delete")
    public String deleteNotes(@RequestParam("id") int noteid, Model model)
    {
        String fileUploadError = "Delete fail";
        if(noteid > 0)
        {
            notesService.deleteFile(noteid);
            model.addAttribute("Success",true);
            return "result";
        }
        model.addAttribute("Unsuccess",fileUploadError);

        return "result";
    }

}
