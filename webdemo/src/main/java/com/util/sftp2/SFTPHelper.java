package com.util.sftp2;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import java.io.InputStream;

public class SFTPHelper {
    private SFTPPool pool;

    public SFTPHelper(SFTPPool pool) {
        this.pool = pool;
    }

    public void download(String dir, String name) {
        ChannelSftp sftp = (ChannelSftp) pool.borrowObject();
        try {
            sftp.cd(dir);
            InputStream is = sftp.get(name);
            //TODO 实际下载
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

}
