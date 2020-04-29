package com.service;

import com.util.sftp2.SFTPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SFTPService {
    @Autowired
    SFTPHelper sftpHelper;

    public void download(String localFile, String ftpFile) {
        sftpHelper.download(localFile, ftpFile);
    }
}
