package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {
    private final FileService fileService;
    private  List<String> fileList  = new ArrayList<>();

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String getFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file , Model model) {
        try {
            String fileUploadError = "upload fail";
            String userName = authentication.getName();
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            List<Files> z = fileService.getAllFiles(userName);

            Files zz = z.stream()
                    .filter(customer -> fileName.equals(customer.getFilename()))
                    .findAny()
                    .orElse(null);

            if (zz != null) {
                model.addAttribute("Unsuccess", "Duplicate file name, file name already present");
                return "result";
            }

            InputStream is = file.getInputStream();

            ByteArrayOutputStream input = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[8192];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                input.write(buffer, 0, len);
            }

            byte[] t = input.toByteArray();

            int row = fileService.fileUpload(userName, fileName, contentType, t);
            if (row > 0) {

                model.addAttribute("Success", true);
            } else {
                model.addAttribute("Unsuccess", fileUploadError);
            }

            return "result";
        }
        catch(Exception e)
        {
            model.addAttribute("Unsuccess", "error");
            return "result";
        }
    }

    @GetMapping("/file-upload/delete")
    public String delete(@RequestParam("id") int fileID ,Model model)
    {
        String fileUploadError = "Delete fail";
        if(fileID > 0)
        {
            fileService.deleteFile(fileID);
            model.addAttribute("Success",true);
            return "result";
        }
        model.addAttribute("Unsuccess",fileUploadError);

        return "result";
    }

    @GetMapping("/file-upload/download")
    public ResponseEntity<Resource> download(@RequestParam("id") int fileID , Model model) throws IOException {
               return fileService.downloadFile(fileID);
    }
}
