package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credential")
    public String addCredentials(Authentication authentication , Credentials credentials , Model model)
    {
       String t = authentication.getName();
       int id = userMapper.getUser(t).getUserId();
        String key = RandomStringUtils.random(16, true, true);
        credentials.setPassword(encryptionService.encryptValue(credentials.getPassword() , key));
        credentials.setKey(key);
       int row = credentialService.addCredentials(credentials, id);
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

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam("id") int credentialId, Model model)
    {
        String fileUploadError = "Delete fail";
        if(credentialId > 0)
        {
            credentialService.deleteCredential(credentialId);
            model.addAttribute("Success",true);
            return "result";
        }
        model.addAttribute("Unsuccess",fileUploadError);

        return "result";
    }
}
