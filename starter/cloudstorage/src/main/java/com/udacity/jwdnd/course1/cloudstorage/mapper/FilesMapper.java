package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES Where userid = #{userid} ")
    List<Files> getFiles(Integer userid);

    @Insert("INSERT INTO FILES(fileId ,filename,contenttype,filesize,userid,filedata) VALUES(#{fileId},#{filename},#{contenttype},#{filesize}, #{userid},#{filedata})")
    @Options(useGeneratedKeys = true , keyProperty = "fileId")
    int insert(Files file);

    @Select("SELECT * FROM FILES")
    List<Files> findAll();

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    public int deleteFile(int fileid);

    @Select("SELECT * FROM FILES Where fileId = #{fileId} ")
    Files getFilesWithId(Integer fileid);
}
