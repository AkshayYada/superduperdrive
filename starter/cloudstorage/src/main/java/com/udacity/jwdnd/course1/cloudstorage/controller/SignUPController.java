package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUPController {
    private final UserService userService;

    public SignUPController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signUp()
    {
        return "signup";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user , Model model, RedirectAttributes redirectAttributes)
    {
        String signupError = null;

        if (userService.isUserExist(user.getUsername()))
            signupError = "user name already exists";

        if(signupError == null)
        {
            int rowAdd = userService.createUser(user);

            if(rowAdd < 0)
            {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if(signupError !=null)
        {
            model.addAttribute("signupError",signupError);
        }
        else
        {
            redirectAttributes.addFlashAttribute("SuccessMessage","Sign Up Successfully");
            return "redirect:/login";
           // model.addAttribute("signupSuccess",true);
        }

        return "signup";
    }


}
