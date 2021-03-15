package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final UserMapper userMapper;
    private final NotesMapper notesMapper;

    public NotesService(UserMapper userMapper,  NotesMapper notesMapper) {
        this.userMapper = userMapper;
        this.notesMapper = notesMapper;
    }

    public List getAllNotes(String name ) {
        try {
            User user = userMapper.getUser(name);
            int userID = user.getUserId();
            return notesMapper.getNotes(userID);
        } catch (Exception e)
        {
            return null;
        }

    }

    public int addNotesAndDescription(Notes note, String userName) {
        User user = userMapper.getUser(userName);
        int userID = user.getUserId();
        if(note.getNoteid() != null && note.getNoteid() > 0)
        {
         return notesMapper.updateNote(note);
        }
        return notesMapper.insert(new Notes(null,note.getNotetitle(),note.getNotedescription(),userID));
    }

    public int deleteFile(int noteid) {
        return notesMapper.deleteNotes(noteid);
    }
}
