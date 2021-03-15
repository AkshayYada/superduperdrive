package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.catalina.LifecycleState;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class FileService {
    private final FilesMapper filesMapper;
    private final UserMapper userMapper;

    public FileService(FilesMapper filesMapper, UserMapper userMapper) {
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
    }


    public int fileUpload(String userName, String fileName, String contentType, byte[] t) {
        User user = userMapper.getUser(userName);
        String length = String.valueOf(t.length);
      return filesMapper.insert(new Files(null,fileName,contentType,length,user.getUserId(),t));
    }

    public List<Files> getAllFiles(String name) {
        try {
            User user = userMapper.getUser(name);
            int userID = user.getUserId();
            return filesMapper.getFiles(userID);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    public void deleteFile(int fileID) {
        filesMapper.deleteFile(fileID);
    }

    public ResponseEntity<Resource> downloadFile(int fileID) throws IOException {
        Files file =  filesMapper.getFilesWithId(fileID);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");

        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");

        httpHeaders.add("Pragma", "no-cache");

        httpHeaders.add("Expires", "0");
        httpHeaders.add("Content-type" , file.getContenttype());

        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

        return ResponseEntity.ok().headers(httpHeaders).body(resource);

    }
}
