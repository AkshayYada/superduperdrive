package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS Where userid = #{userid} ")
    List<Credentials> getCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS(credentialid ,url,username,key,password,userid) VALUES(#{credentialid},#{url},#{username},#{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true , keyProperty = "credentialid")
    int insert(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username},key = #{key},password = #{password}  WHERE credentialid = #{credentialid}")
    public int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public int deletecredentials(int credentialid);
}
