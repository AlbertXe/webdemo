package com.util.sftp;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class SftpInfo {
    private String ip;
    private String port;
    private String username;
    private String pwd;

    private String isPool;
    private int channelNum;

    private String operFlag;
    private String localFile;
    private String directoryFile;

}
