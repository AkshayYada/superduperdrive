package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES Where userid = #{userid} ")
    List<Notes> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES(noteid ,notetitle,notedescription,userid) VALUES(#{noteid},#{notetitle},#{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true , keyProperty = "noteid")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    public int deleteNotes(int noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    public int updateNote(Notes note);
}
