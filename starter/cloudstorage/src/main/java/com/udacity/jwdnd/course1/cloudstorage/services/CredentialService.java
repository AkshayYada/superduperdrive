package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public CredentialResponseService getResponseFile(Credentials credentials) {
        CredentialResponseService credResponsiveService = new CredentialResponseService();
        credResponsiveService.setEncpassword(credentials.getPassword());
        String decpassword = decryptPassword(credentials).getPassword();
        credResponsiveService.setPassword(decpassword);
        credResponsiveService.setUrl(credentials.getUrl());
        credResponsiveService.setUsername(credentials.getUsername());
        credResponsiveService.setCredentialid(credentials.getCredentialid());
        return credResponsiveService;
    }

    public List<CredentialResponseService> getCredential(String name) {
        User user = userMapper.getUser(name);
        int id = user.getUserId();
        List<Credentials> list = credentialsMapper.getCredentials(id);


        return list.stream().map(this::getResponseFile).collect(Collectors.toList());

    }

       public Credentials decryptPassword(Credentials credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public int addCredentials(Credentials credentials, int id) {

        if (credentials.getCredentialid() != null && credentials.getCredentialid() >0)
        {
            return credentialsMapper.updateCredentials(credentials);
        }

        return credentialsMapper.insert(new Credentials(null,credentials.getUrl(),credentials.getUsername(),credentials.getKey(),credentials.getPassword(),id));
    }

    public int deleteCredential(int credentialId) {
        return credentialsMapper.deletecredentials(credentialId);
    }
}
